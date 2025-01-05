package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.factory.BankAccountDataFactory;
import io.dflowers.remittanceservice.factory.UserDataFactory;
import io.dflowers.remittanceservice.repository.BankAccountRepository;
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
    private WithdrawBankAccount depositBankAccount;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

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
        var sut = depositBankAccount.invoke(bankAccount.id(), subtractedBalance);
        var expected = bankAccount.withdraw(subtractedBalance).balance();

        assertEquals(0, sut.balance().compareTo(expected));
    }

    @Test
    public void testShouldThrowNotFoundExceptionWhenFindInvalidId() {
        var invalidId = 0;
        Exception exception = assertThrows(
            NotFoundException.class,
            () -> depositBankAccount.invoke(invalidId, new BigDecimal(50000))
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
            () -> depositBankAccount.invoke(bankAccount.id(), withdrawAmount)
        );

        assertEquals(
            "You have an insufficient balance",
            exception.getMessage()
        );
    }
}
