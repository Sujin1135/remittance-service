package io.dflowers.remittanceservice.factory;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.domain.TransactionType;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import net.jqwik.api.Arbitraries;

public class BankTransactionFactory {
    public static BankTransaction generate(long accountId, long relatedAccountId) {
        FixtureMonkey sut = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();
        return sut.giveMeBuilder(BankTransaction.class)
            .set(javaGetter(BankTransaction::id), 0)
            .set(javaGetter(BankTransaction::accountId), accountId)
            .set(javaGetter(BankTransaction::amount), new BigDecimal(50000))
            .set(javaGetter(BankTransaction::relatedAccountId), relatedAccountId)
            .set(javaGetter(BankTransaction::transactionType), Arbitraries.of(TransactionType.values()))
            .set(javaGetter(BankTransaction::balanceAfter), new BigDecimal(50000))
            .set(javaGetter(BankTransaction::fee), BigDecimal.ZERO)
            .set(javaGetter(BankAccount::created), OffsetDateTime.now())
            .sample();
    }
}
