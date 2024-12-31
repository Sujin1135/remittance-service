package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankAccountRepository extends JpaRepository<BankAccountEntity, Long> {}
