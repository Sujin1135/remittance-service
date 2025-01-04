package io.dflowers.remittanceservice.entity;

import io.dflowers.remittanceservice.domain.DailyLimit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@IdClass(AccountDateId.class)
@Table(name = "daily_limits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyLimitEntity {

    public DailyLimitEntity(DailyLimit domain) {
        this.accountId = domain.accountId();
        this.date = domain.date();
        this.dailyWithdrawLimit = domain.dailyWithdrawLimit();
        this.dailyTransferLimit = domain.dailyTransferLimit();
        this.totalWithdraw = domain.totalWithdraw();
        this.totalTransfer = domain.totalTransfer();
        this.created = domain.created();
        this.modified = domain.modified();
    }

    @Id
    private long accountId;

    @Id
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal dailyWithdrawLimit;

    @Column(nullable = false)
    private BigDecimal dailyTransferLimit;

    @Column(nullable = false)
    private BigDecimal totalWithdraw;

    @Column(nullable = false)
    private BigDecimal totalTransfer;

    @Column(nullable = false)
    private OffsetDateTime created;

    @Column(nullable = false)
    private OffsetDateTime modified;

    public DailyLimit toDomain() {
        return new DailyLimit(
            accountId,
            date,
            dailyWithdrawLimit,
            dailyTransferLimit,
            totalWithdraw,
            totalTransfer,
            created,
            modified
        );
    }
}

