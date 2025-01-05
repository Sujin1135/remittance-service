package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.entity.TransactionEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    @Override
    public List<BankTransaction> findByAccountId(long accountId, long cursor) {
        return jpaTransactionRepository
            .findByAccountIdOrderAndCursorByCreatedDesc(
                accountId,
                cursor,
                PageRequest.of(0, 10)
            )
            .stream().map(TransactionEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public BankTransaction save(BankTransaction bankTransaction) {
        return jpaTransactionRepository
            .save(new TransactionEntity(bankTransaction))
            .toDomain();
    }
}
