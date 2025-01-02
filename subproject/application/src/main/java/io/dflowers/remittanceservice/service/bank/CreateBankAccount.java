package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import io.dflowers.remittanceservice.repository.UserRepository;
import io.dflowers.remittanceservice.service.exception.DuplicatedException;
import io.dflowers.remittanceservice.service.exception.NotFoundException;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class CreateBankAccount implements Function<BankAccount, BankAccount> {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public CreateBankAccount(
        BankAccountRepository bankAccountRepository,
        UserRepository userRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BankAccount apply(BankAccount params) throws NotFoundException {
        validateUser(params);

        return bankAccountRepository.save(params);
    }

    private void validateUser(BankAccount params) throws NotFoundException, DuplicatedException {
        userRepository.findOne(
            params.userId()).orElseThrow(() -> new NotFoundException("User not found")
        );

        bankAccountRepository.findByAccountNumber(params.accountNumber())
            .ifPresent(data -> {
                throw new DuplicatedException("Account number is already exists");
            });
    }
}
