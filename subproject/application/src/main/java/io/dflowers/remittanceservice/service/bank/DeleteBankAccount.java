package io.dflowers.remittanceservice.service.bank;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBankAccount {

    private final BankAccountRepository bankAccountRepository;

    public void invoke(long id, long userId) throws NotFoundException, BadRequestException {
        BankAccount bankAccount = getBankAccount(id, userId);

        bankAccountRepository.save(bankAccount.delete());
    }

    private BankAccount getBankAccount(long id, long userId) throws NotFoundException, BadRequestException {
        Optional<BankAccount> result = bankAccountRepository.findByIdAndUserId(id, userId);

        if (result.isEmpty()) {
            throw new NotFoundException(
                String.format("Bank account is not found by id(%d) and userId(%d)", id, userId)
            );
        }

        BankAccount bankAccount = result.get();

        if (bankAccount.deleted() != null) {
            throw new BadRequestException(String.format("This bank account was deleted by id(%d)", id));
        }
        return bankAccount;
    }
}
