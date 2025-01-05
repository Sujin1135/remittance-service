package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.entity.TransactionEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    @Override
    public List<BankTransaction> findByAccountId(long accountId) {
        return jpaTransactionRepository
            .findByAccountIdOrRelatedAccountIdOrderByCreatedDesc(accountId)
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
