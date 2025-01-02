package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import io.dflowers.remittanceservice.repository.UserRepository;
import io.dflowers.remittanceservice.service.exception.BadRequestException;
import io.dflowers.remittanceservice.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreateBankAccount {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public CreateBankAccount(
        BankAccountRepository bankAccountRepository,
        UserRepository userRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public BankAccount invoke(BankAccount params) throws NotFoundException, BadRequestException {
        validate(params);

        return bankAccountRepository.save(params);
    }

    private void validate(BankAccount params) throws NotFoundException, BadRequestException {
        userRepository.findOne(
            params.userId()).orElseThrow(() -> new NotFoundException("User not found")
        );

        if (bankAccountRepository.findByAccountNumber(params.accountNumber()).isPresent()) {
            throw new BadRequestException("Account number is already exists");
        }
    }
}
