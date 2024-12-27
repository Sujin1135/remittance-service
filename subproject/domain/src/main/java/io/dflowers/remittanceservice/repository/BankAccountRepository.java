package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankAccount;
import java.util.UUID;

public interface BankAccountRepository {
    BankAccount findById(UUID id);
}
