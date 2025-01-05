package io.dflowers.remittanceservice.domain;

import jakarta.annotation.Nullable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.With;

@With
public record BankAccount(
    long id,
    long userId,
    String name,
    Bank bank,
    String accountNumber,
    BigDecimal balance,
    BigDecimal dailyWithdrawLimit,
    BigDecimal dailyTransferLimit,
    OffsetDateTime created,
    OffsetDateTime modified,
    @Nullable
    OffsetDateTime deleted
) {

    public static BigDecimal FEE_LATE = BigDecimal.valueOf(0.01);

    public static BankAccount of(
        long userId,
        String name,
        Bank bank,
        String accountNumber,
        BigDecimal balance
    ) {
        var now = OffsetDateTime.now();
        return new BankAccount(
            0,
            userId,
            name,
            bank,
            accountNumber,
            balance,
            DailyLimit.DEFAULT_WITHDRAW_LIMIT,
            DailyLimit.DEFAULT_TRANSFER_LIMIT,
            now,
            now,
            null
        );
    }

    public BankAccount delete() {
        var now = OffsetDateTime.now();
        return this.withDeleted(now).withModified(now);
    }

    public BankAccount withdraw(BigDecimal amount) {
        return this.withBalance(this.balance.subtract(amount));
    }

    public BankAccount deposit(BigDecimal amount) {
        return this.withBalance(this.balance.add(amount));
    }
    
    public BankAccount transfer(BigDecimal amount) {
        var fee = calcFee(amount);
        var deductedAmount = this.balance.subtract(amount);
        var deductedFee = deductedAmount.subtract(fee);

        return this.withBalance(deductedFee);
    }

    public BigDecimal calcFee(BigDecimal amount) {
        return amount.multiply(FEE_LATE);
    }
}
