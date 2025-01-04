package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.AccountDateId;
import io.dflowers.remittanceservice.entity.DailyLimitEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDailyLimitEntityRepository extends JpaRepository<DailyLimitEntity, AccountDateId> {

    Optional<DailyLimitEntity> findByAccountIdAndDate(long accountId, LocalDate date);
}
