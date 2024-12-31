package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankAccount;
import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> findById(long id);
}
