package io.dflowers.remittanceservice.service;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.domain.DailyLimit;
import io.dflowers.remittanceservice.domain.TransactionType;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.ErrorCode;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import io.dflowers.remittanceservice.repository.DailyLimitRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferBankAccount {

    private final BankAccountRepository bankAccountRepository;
    private final DailyLimitRepository dailyLimitRepository;
    private final SaveTransaction saveTransaction;

    @Transactional
    public BankAccount invoke(
        long id,
        long receiverId,
        BigDecimal amount
    ) throws NotFoundException, BadRequestException {
        var sender = getBankAccountWithBalanceSubtracted(id, amount);

        saveWithdraw(id, amount);
        var receiver = transferToReceiver(receiverId, amount);

        saveTransaction.invoke(
            id,
            receiverId,
            amount,
            sender.balance(),
            sender.calcFee(amount),
            TransactionType.SEND
        );
        saveTransaction.invoke(
            receiverId,
            id,
            amount,
            receiver.balance(),
            TransactionType.RECEIVED
        );

        return sender;
    }

    private BankAccount getBankAccountWithBalanceSubtracted(long id, BigDecimal amount)
        throws NotFoundException, BadRequestException {
        var found = bankAccountRepository.findById(id).orElseThrow(
            () -> new NotFoundException(String.format("Sender bank account was not found by id(%d)",
                id))
        );
        var sender = found.transfer(amount);

        if (sender.balance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(
                ErrorCode.LACK_OF_BALANCE,
                "Insufficient balance for this transaction"
            );
        }
        return bankAccountRepository.save(sender);
    }

    private BankAccount transferToReceiver(
        long receiverId,
        BigDecimal amount
    ) throws NotFoundException {
        var found = bankAccountRepository.findById(receiverId).orElseThrow(
            () -> new NotFoundException(String.format("Receiver bank account was not found by id(%d)",
                receiverId))
        );
        return bankAccountRepository.save(found.deposit(amount));
    }

    private DailyLimit saveWithdraw(
        long accountId,
        BigDecimal amount
    ) throws BadRequestException {
        var found = dailyLimitRepository
            .findByAccountIdAndDate(accountId, LocalDate.now())
            .orElse(DailyLimit.of(accountId));
        var updated = found.saveTransfer(amount);

        if (updated.dailyTransferLimit().compareTo(updated.totalTransfer()) < 0) {
            throw new BadRequestException(
                ErrorCode.EXCEEDED_TRANSFER_LIMIT,
                String.format(
                    "Today's transfer(%s) limit has been exceeded",
                    updated.dailyTransferLimit()
                )
            );
        }

        return dailyLimitRepository.save(updated);
    }
}
