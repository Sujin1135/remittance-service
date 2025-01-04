package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.DailyLimit;
import java.time.LocalDate;
import java.util.Optional;

public interface DailyLimitRepository {
    Optional<DailyLimit> findByAccountIdAndDate(long id, LocalDate date);
    DailyLimit save(DailyLimit dailyLimit);
}
