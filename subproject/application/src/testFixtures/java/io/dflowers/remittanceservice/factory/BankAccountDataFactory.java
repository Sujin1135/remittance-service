package io.dflowers.remittanceservice.factory;

import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import java.math.BigDecimal;

public class BankAccountDataFactory {
    public static BankAccount generate(long userId) {
        return BankAccount.of(
            userId,
            "일반계좌",
            Bank.KAKAO,
            "756002-00-014380",
            BigDecimal.ZERO
        );
    }
}
