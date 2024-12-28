package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.BankAccount;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Entity(name = "bank_accounts")
@Getter
public class BankAccountEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private OffsetDateTime created;

    @Column(nullable = false)
    private OffsetDateTime modified;

    @Nullable
    @Column(nullable = true)
    private OffsetDateTime deleted;

    public BankAccount toDomain() {
        return new BankAccount(
            id = id,
            name = name,
            balance = balance,
            created = created,
            modified = modified,
            deleted = deleted
        );
    }
}
