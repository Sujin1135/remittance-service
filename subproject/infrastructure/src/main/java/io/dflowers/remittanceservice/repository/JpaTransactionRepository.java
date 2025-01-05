package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.TransactionEntity;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query(
        "SELECT t FROM TransactionEntity t " +
            "WHERE t.accountId = :accountId AND t.id > :cursor " +
            "ORDER BY t.id"
    )
    List<TransactionEntity> findByAccountIdOrderAndCursorByCreatedDesc(
        @Param("accountId") long accountId,
        @Param("cursor") long cursor,
        Pageable pageable
    );
}
