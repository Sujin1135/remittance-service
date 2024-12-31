package io.dflowers.remittanceservice.domain;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BankAccount(
    long id,
    String name,
    Bank bank,
    String account_number,
    BigDecimal balance,
    OffsetDateTime created,
    OffsetDateTime modified,
    @Nullable
    OffsetDateTime deleted
) {}
