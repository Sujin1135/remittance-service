package io.dflowers.remittanceservice.service;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.domain.TransactionType;
import io.dflowers.remittanceservice.repository.TransactionRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SaveTransaction {

    private final TransactionRepository transactionRepository;

    protected BankTransaction invoke(
        long id,
        long relatedAccountId,
        BigDecimal amount,
        BigDecimal afterBalance,
        TransactionType transactionType
    ) {
        var transaction = BankTransaction.of(
            id,
            relatedAccountId,
            transactionType,
            amount,
            BigDecimal.ZERO,
            afterBalance
        );

        return transactionRepository.save(transaction);
    }

    protected BankTransaction invoke(
        long id,
        long relatedAccountId,
        BigDecimal amount,
        BigDecimal afterBalance,
        BigDecimal fee,
        TransactionType transactionType
    ) {
        var transaction = BankTransaction.of(
            id,
            relatedAccountId,
            transactionType,
            amount,
            fee,
            afterBalance
        );

        return transactionRepository.save(transaction);
    }
}
