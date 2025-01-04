package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.domain.DailyLimit;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import io.dflowers.remittanceservice.repository.DailyLimitRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawBankAccount {

    private final BankAccountRepository bankAccountRepository;
    private final DailyLimitRepository dailyLimitRepository;

    public BankAccount invoke(
        long id,
        BigDecimal amount
    ) throws NotFoundException, BadRequestException {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(
            () -> new NotFoundException(String.format("Bank account was not found by id(%d)", id))
        );
        BankAccount updated = bankAccount.subtract(amount);

        if (updated.balance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("You have an insufficient balance");
        }

        saveWithdraw(id, amount);

        return bankAccountRepository.save(updated);
    }

    private DailyLimit saveWithdraw(
        long accountId,
        BigDecimal amount
    ) throws BadRequestException {
        var found = dailyLimitRepository
            .findByAccountIdAndDate(accountId, LocalDate.now())
            .orElse(DailyLimit.of(accountId));
        var updated = found.saveWithdraw(amount);

        if (updated.dailyWithdrawLimit().compareTo(updated.totalWithdraw()) < 0) {
            throw new BadRequestException(String.format(
                    "Today's withdrawal(%s) limit has been exceeded",
                    updated.dailyWithdrawLimit()
            ));
        }

        return dailyLimitRepository.save(updated);
    }
}
