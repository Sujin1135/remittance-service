package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "bank_accounts")
@Getter
public class BankAccountEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Bank bank;

    @Column(nullable = false, name = "account_number")
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private OffsetDateTime created;

    @Column(nullable = false)
    private OffsetDateTime modified;

    @Nullable
    @Column()
    private OffsetDateTime deleted;

    public BankAccount toDomain() {
        return new BankAccount(
            id = id,
            name = name,
            bank = bank,
            accountNumber = accountNumber,
            balance = balance,
            created = created,
            modified = modified,
            deleted = deleted
        );
    }
}
