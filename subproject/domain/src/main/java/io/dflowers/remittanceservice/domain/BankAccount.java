package io.dflowers.remittanceservice.domain;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BankAccount(
    long id,
    long userId,
    String name,
    Bank bank,
    String accountNumber,
    BigDecimal balance,
    OffsetDateTime created,
    OffsetDateTime modified,
    @Nullable
    OffsetDateTime deleted
) {
    public static BankAccount of(
        long userId,
        String name,
        Bank bank,
        String accountNumber,
        BigDecimal balance
    ) {
        var now = OffsetDateTime.now();
        return new BankAccount(
            0,
            userId,
            name,
            bank,
            accountNumber,
            balance,
            now,
            now,
            null
        );
    }
}
