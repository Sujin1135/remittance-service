package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.factory.BankAccountDataFactory;
import io.dflowers.remittanceservice.factory.BankTransactionFactory;
import io.dflowers.remittanceservice.factory.UserDataFactory;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
import io.dflowers.remittanceservice.repository.TransactionRepository;
import io.dflowers.remittanceservice.repository.UserRepository;
import java.math.BigDecimal;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;

@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindTransactionsByAccountIdTest {

    @Autowired
    private FindTransactionsByAccountId findTransactionsByAccountId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private BankAccount bankAccount;
    private int limit = 50;

    static MySQLContainer<?> mysql = new MySQLContainer<>(
        "mysql:8.0"
    );

    @BeforeAll
    public static void beforeAll() {
        mysql.start();

        Flyway.configure()
            .dataSource(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword()
            )
            .locations("classpath:db/migration")
            .load()
            .migrate();
    }

    @BeforeEach
    public void beforeEach() {
        bankAccount = bankAccountRepository.save(
            BankAccountDataFactory
                .generate(userRepository.save(UserDataFactory.generate("최민규")).id())
                .withBalance(new BigDecimal(500000))
        );

        for (int i = 0; i < limit; i++) {
            transactionRepository.save(
                BankTransactionFactory.generate(bankAccount.id(),
                    bankAccount.id())
            );
        }
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    public void testShouldReturnTransactionsByDefaultLimit() {
        var sut = findTransactionsByAccountId.invoke(
            bankAccount.id(),
            0
        );

        assertEquals(10, sut.size());
    }

    @Test
    public void testShouldReturnTransactionsByLastCreated() {
        var previous = findTransactionsByAccountId.invoke(
            bankAccount.id(),
            0
        ).getLast();

        var sut = findTransactionsByAccountId.invoke(
            bankAccount.id(),
            previous.id()
        );

        assertEquals(10, sut.size());
    }

    @Test
    public void testShouldReturnEmptyListAfterLastCursor() {
        var sut = findTransactionsByAccountId.invoke(
            bankAccount.id(),
            limit
        );

        assertEquals(true, sut.isEmpty());
    }
}
