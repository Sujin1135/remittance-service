package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Getter;

@Entity
@Table(name = "bank_accounts")
@Getter
public class BankAccountEntity {

    protected BankAccountEntity() {}

    public BankAccountEntity(BankAccount domain) {
        this.id = domain.id();
        this.name = domain.name();
        this.accountNumber = domain.accountNumber();
        this.bank = domain.bank();
        this.userId = domain.userId();
        this.balance = domain.balance();
        this.created = domain.created();
        this.modified = domain.modified();
        this.deleted = domain.deleted();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
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
            userId = userId,
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
