package io.dflowers.remittanceservice.service;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.domain.TransactionType;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositBankAccount {

    private final BankAccountRepository bankAccountRepository;
    private final SaveTransaction saveTransaction;

    @Transactional
    public BankAccount invoke(long id, BigDecimal amount) throws NotFoundException {
        var found = bankAccountRepository.findById(id).orElseThrow(
            () -> new NotFoundException(String.format("Bank account was not found by id(%d)", id))
        );

        var updated = bankAccountRepository.save(found.deposit(amount));

        saveTransaction.invoke(id, id, amount, updated.balance(), TransactionType.DEPOSIT);

        return updated;
    }
}
