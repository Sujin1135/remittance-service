package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.DailyLimit;
import io.dflowers.remittanceservice.entity.DailyLimitEntity;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DailyLimitRepositoryImpl implements DailyLimitRepository {

    private final JpaDailyLimitEntityRepository jpaDailyLimitEntityRepository;

    @Override
    public Optional<DailyLimit> findByAccountIdAndDate(long id, LocalDate date) {
        return jpaDailyLimitEntityRepository
            .findByAccountIdAndDate(id, date)
            .map(DailyLimitEntity::toDomain);
    }

    @Override
    public DailyLimit save(DailyLimit dailyLimit) {
        return jpaDailyLimitEntityRepository
            .save(new DailyLimitEntity(dailyLimit))
            .toDomain();
    }
}
