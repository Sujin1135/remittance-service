package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.service.exception.NotFoundException;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class FindBankById implements Function<Long, BankAccount> {
    private final BankAccountRepository bankAccountRepository;

    public FindBankById(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount apply(Long id) throws NotFoundException {
        return bankAccountRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Bank account not found"));
    }
}
