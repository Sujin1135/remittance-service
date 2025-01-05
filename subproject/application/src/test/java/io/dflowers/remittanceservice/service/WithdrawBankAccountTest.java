package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.domain.TransactionType;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.factory.BankAccountDataFactory;
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
public class WithdrawBankAccountTest {

    @Autowired
    private WithdrawBankAccount withdrawBankAccount;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private BankAccount bankAccount;

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
    public void testShouldReturnSubtractedBalance() throws NotFoundException, BadRequestException {
        var subtractedBalance = new BigDecimal(5000);
        var sut = withdrawBankAccount.invoke(bankAccount.id(), subtractedBalance);
        var expected = bankAccount.withdraw(subtractedBalance).balance();

        assertEquals(0, sut.balance().compareTo(expected));
    }

    @Test
    public void testShouldThrowNotFoundExceptionWhenFindInvalidId() {
        var invalidId = 0;
        Exception exception = assertThrows(
            NotFoundException.class,
            () -> withdrawBankAccount.invoke(invalidId, new BigDecimal(50000))
        );

        assertEquals(
            String.format("Bank account was not found by id(%d)", invalidId),
            exception.getMessage()
        );
    }

    @Test
    public void testShouldThrowBadRequestExceptionCauseLackOfBalance() {
        var withdrawAmount = bankAccount.balance().add(new BigDecimal(10000));

        Exception exception = assertThrows(
            BadRequestException.class,
            () -> withdrawBankAccount.invoke(bankAccount.id(), withdrawAmount)
        );

        assertEquals(
            "You have an insufficient balance",
            exception.getMessage()
        );
    }

    @Test
    public void testShouldReturnCorrectlyTransactionData() throws NotFoundException, BadRequestException {
        var amount = new BigDecimal(5000);
        var afterBankAccount = withdrawBankAccount.invoke(bankAccount.id(), amount);
        var transaction = transactionRepository.findByAccountId(bankAccount.id()).stream().filter(
            (t) -> t.transactionType() == TransactionType.WITHDRAW
        ).toList().getFirst();

        assertEquals(transaction.balanceAfter().compareTo(bankAccount.balance().subtract(amount)), 0);
        assertEquals(transaction.balanceAfter().compareTo(afterBankAccount.balance()), 0);
        assertEquals(transaction.amount().compareTo(amount), 0);
    }
}
