package io.dflowers.remittanceservice.domain;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BankAccount(
    UUID id,
    String name,
    BigDecimal balance,
    OffsetDateTime created,
    OffsetDateTime modified,
    @Nullable
    OffsetDateTime deleted
) {}
