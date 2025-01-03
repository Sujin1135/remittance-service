package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankAccount;
import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> findById(long id);
    Optional<BankAccount> findByIdAndUserId(long id, long userId);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    BankAccount save(BankAccount bankAccount);
}
