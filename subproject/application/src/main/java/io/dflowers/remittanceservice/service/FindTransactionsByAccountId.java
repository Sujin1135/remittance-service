package io.dflowers.remittanceservice.service;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.repository.TransactionRepository;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindTransactionsByAccountId {

    private final TransactionRepository transactionRepository;

    public List<BankTransaction> invoke(
        long accountId,
        long cursor
    ) {
        return transactionRepository.findByAccountId(accountId, cursor);
    }
}
