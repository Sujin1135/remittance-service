package io.dflowers.remittanceservice.repository;

import io.dflowers.remittanceservice.domain.BankTransaction;
import java.util.List;

public interface TransactionRepository {
    List<BankTransaction> findByAccountId(long accountId);
    BankTransaction save(BankTransaction bankTransaction);
}
