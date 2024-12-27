package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.BankAccountEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccountEntity, UUID> {
    List<BankAccountEntity> findBankAccountEntityById(UUID id);
}
