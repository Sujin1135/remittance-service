package io.dflowers.remittanceservice.service.bank;

import exception.NotFoundException;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import java.util.UUID;
import java.util.function.Function;

public class FindBankById implements Function<UUID, BankAccount> {
    private final BankAccountRepository bankAccountRepository;

    public FindBankById(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount apply(UUID id) throws NotFoundException {
        return bankAccountRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Bank account not found"));
    }
}
