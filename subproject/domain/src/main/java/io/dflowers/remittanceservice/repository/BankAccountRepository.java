package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankAccount;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {
    Optional<BankAccount> findById(UUID id);
}