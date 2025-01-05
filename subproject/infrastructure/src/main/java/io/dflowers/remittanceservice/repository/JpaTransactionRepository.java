package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.entity.TransactionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query(
        "SELECT t FROM TransactionEntity t " +
            "WHERE t.accountId = :accountId OR t.relatedAccountId = :accountId " +
            "ORDER BY t.created DESC"
    )
    List<TransactionEntity> findByAccountIdOrRelatedAccountIdOrderByCreatedDesc(
        @Param("accountId") long accountId
    );
}
