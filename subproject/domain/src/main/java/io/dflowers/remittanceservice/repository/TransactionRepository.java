package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankTransaction;
import java.time.OffsetDateTime;
import java.util.List;

public interface TransactionRepository {
    List<BankTransaction> findByAccountId(long accountId, long cursor);
    BankTransaction save(BankTransaction bankTransaction);
}
