package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.entity.BankAccountEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BankAccountRepositoryImpl implements BankAccountRepository {

    private final JpaBankAccountRepository jpaBankAccountRepository;

    public BankAccountRepositoryImpl(JpaBankAccountRepository jpaBankAccountRepository) {
        this.jpaBankAccountRepository = jpaBankAccountRepository;
    }

    @Override
    public Optional<BankAccount> findById(long id) {
        return jpaBankAccountRepository
            .findById(id)
            .map(BankAccountEntity::toDomain);
    }
}
