package io.dflowers.remittanceservice.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.With;

@With
public record BankTransaction(
    long id,
    long accountId,
    Long relatedAccountId,
    TransactionType transactionType,
    BigDecimal amount,
    BigDecimal fee,
    BigDecimal balanceAfter,
    OffsetDateTime created
) {
    public static BankTransaction of(
        long accountId,
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal fee,
        BigDecimal balanceAfter
    ) {
        return new BankTransaction(
            0,
            accountId,
            accountId,
            transactionType,
            amount,
            fee,
            balanceAfter,
            OffsetDateTime.now()
        );
    }

    public static BankTransaction of(
        long accountId,
        long relatedAccountId,
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal fee,
        BigDecimal balanceAfter
    ) {
        return new BankTransaction(
            0,
            accountId,
            relatedAccountId,
            transactionType,
            amount,
            fee,
            balanceAfter,
            OffsetDateTime.now()
        );
    }
}
