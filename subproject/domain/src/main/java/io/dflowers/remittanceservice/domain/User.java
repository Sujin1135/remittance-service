package io.dflowers.remittanceservice.domain;

import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;

public record User(
    long id,
    String name,
    OffsetDateTime created,
    OffsetDateTime modified,
    @Nullable
    OffsetDateTime deleted
) {}
