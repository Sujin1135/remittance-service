package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;

@Entity
@Table(name = "bank_accounts")
@Getter
public class BankAccountEntity {
    public static BankAccountEntity from(BankAccount bankAccount) {
        return new BankAccountEntity(
            bankAccount.name(),
            bankAccount.accountNumber(),
            bankAccount.bank(),
            bankAccount.balance(),
            bankAccount.created(),
            bankAccount.modified()
        );
    }

    protected BankAccountEntity() {}

    public BankAccountEntity(
        String name,
        String accountNumber,
        Bank bank,
        BigDecimal balance,
        OffsetDateTime created,
        OffsetDateTime modified
    ) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.userId = 1L;
        this.balance = balance;
        this.created = created;
        this.modified = modified;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

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
