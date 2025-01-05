package io.dflowers.remittanceservice.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.With;

@With
public record DailyLimit(
    long accountId,
    LocalDate date,
    BigDecimal dailyWithdrawLimit,
    BigDecimal dailyTransferLimit,
    BigDecimal totalWithdraw,
    BigDecimal totalTransfer,
    OffsetDateTime created,
    OffsetDateTime modified
) {
    public static BigDecimal DEFAULT_WITHDRAW_LIMIT = new BigDecimal(1_000_000);
    public static BigDecimal DEFAULT_TRANSFER_LIMIT = new BigDecimal(3_000_000);

    public static DailyLimit of(long accountId) {
        var now = OffsetDateTime.now();
        return new DailyLimit(
            accountId,
            LocalDate.now(),
            DEFAULT_WITHDRAW_LIMIT,
            DEFAULT_TRANSFER_LIMIT,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            now,
            now
        );
    }

    public DailyLimit saveWithdraw(BigDecimal amount) {
        return this.withTotalWithdraw(totalWithdraw.add(amount));
    }

    public DailyLimit saveTransfer(BigDecimal amount) {
        return this.withTotalTransfer(totalTransfer.add(amount));
    }
}
