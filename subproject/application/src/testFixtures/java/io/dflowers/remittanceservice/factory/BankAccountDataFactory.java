package io.dflowers.remittanceservice.factory;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import net.jqwik.api.Arbitraries;

public class BankAccountDataFactory {
    public static BankAccount generate(long userId) {
        var now = OffsetDateTime.now();
        FixtureMonkey sut = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();
        return sut.giveMeBuilder(BankAccount.class)
            .set(javaGetter(BankAccount::userId), userId)
            .set(
                javaGetter(BankAccount::name),
                Arbitraries.strings()
                    .withChars('가', '힣')
                    .ofMinLength(2)
                    .ofMaxLength(4)
            )
            .set(
                javaGetter(BankAccount::accountNumber),
                Arbitraries.strings()
                    .numeric()
                    .ofMinLength(13)
                    .ofMaxLength(16)
            )
            .set(javaGetter(BankAccount::bank), Arbitraries.of(Bank.values()))
            .set(javaGetter(BankAccount::balance), BigDecimal.ZERO)
            .set(javaGetter(BankAccount::created), now)
            .set(javaGetter(BankAccount::modified), now)
            .sample();
    }
}
