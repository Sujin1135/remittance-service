package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.domain.TransactionType;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionEntity {

    public TransactionEntity(BankTransaction bankTransaction) {
        this.id = bankTransaction.id();
        this.accountId = bankTransaction.accountId();
        this.relatedAccountId = bankTransaction.relatedAccountId();
        this.transactionType = bankTransaction.transactionType();
        this.amount = bankTransaction.amount();
        this.fee = bankTransaction.fee();
        this.balanceAfter = bankTransaction.balanceAfter();
        this.created = bankTransaction.created();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "account_id")
    private long accountId;

    @Column(nullable = false, name = "related_account_id")
    private long relatedAccountId;

    @Column(nullable = false, name = "transaction_type")
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false, name = "amount")
    private BigDecimal amount;

    @Column(nullable = false, name = "fee")
    private BigDecimal fee;

    @Column(nullable = false, name = "balance_after")
    private BigDecimal balanceAfter;

    @Column(nullable = false, name = "created")
    private OffsetDateTime created;

    public BankTransaction toDomain() {
        return new BankTransaction(
            id = id,
            accountId = accountId,
            relatedAccountId = relatedAccountId,
            transactionType = transactionType,
            amount = amount,
            fee = fee,
            balanceAfter = balanceAfter,
            created = created
        );
    }
}
