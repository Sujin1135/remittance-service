package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class CreateBankAccount implements Function<BankAccount, BankAccount> {
    private final BankAccountRepository bankAccountRepository;

    public CreateBankAccount(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount apply(BankAccount params) {
        return bankAccountRepository.save(params);
    }
}
