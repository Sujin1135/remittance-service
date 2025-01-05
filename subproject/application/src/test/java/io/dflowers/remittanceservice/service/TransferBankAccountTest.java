package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.ErrorCode;
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
public class TransferBankAccountTest {

    @Autowired
    private TransferBankAccount transferBankAccount;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private BankAccount sender;
    private BankAccount receiver;

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
        sender = bankAccountRepository.save(
            BankAccountDataFactory
                .generate(userRepository.save(UserDataFactory.generate("최민규")).id())
                .withBalance(new BigDecimal(5000000))
        );
        receiver = bankAccountRepository.save(
            BankAccountDataFactory
                .generate(userRepository.save(UserDataFactory.generate("Susan")).id())
                .withBalance(new BigDecimal(50000))
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
    public void testShouldReturnDeductedBalanceAfterTransfer()
        throws NotFoundException, BadRequestException {
        var amount = new BigDecimal(500000);
        var sut = transferBankAccount.invoke(sender.id(), receiver.id(), amount);

        assertEquals(
            sut.balance().compareTo(new BigDecimal(4495000)),
            0
        );
    }

    @Test
    public void testShouldFindAddedBalanceOfReceiverAccountAfterTransfer()
        throws NotFoundException, BadRequestException {
        var amount = new BigDecimal(500000);
        transferBankAccount.invoke(sender.id(), receiver.id(), amount);

        var sut = bankAccountRepository.findById(receiver.id()).get();

        assertEquals(sut.balance().compareTo(receiver.balance().add(amount)), 0);
    }

    @Test
    public void testShouldThrowNotFoundExceptionWhenFindSenderInvalidId() {
        var invalidId = 0;
        Exception exception = assertThrows(
            NotFoundException.class,
            () -> transferBankAccount.invoke(invalidId, receiver.id(), BigDecimal.TEN)
        );

        assertEquals(
            String.format("Sender bank account was not found by id(%d)", invalidId),
            exception.getMessage()
        );
    }

    @Test
    public void testShouldThrowNotFoundExceptionWhenFindReceiverInvalidId() {
        var invalidId = 0;
        Exception exception = assertThrows(
            NotFoundException.class,
            () -> transferBankAccount.invoke(sender.id(), invalidId, BigDecimal.TEN)
        );

        assertEquals(
            String.format("Receiver bank account was not found by id(%d)", invalidId),
            exception.getMessage()
        );
    }

    @Test
    public void testShouldThrowBadRequestExceptionCauseLackOfMoney() {
        var amount = sender.balance().add(BigDecimal.TEN);

        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> transferBankAccount.invoke(sender.id(), receiver.id(), amount)
        );

        assertEquals(
            ErrorCode.LACK_OF_BALANCE,
            exception.getCode()
        );

        assertEquals(
            "Insufficient balance for this transaction",
            exception.getMessage()
        );
    }

    @Test
    public void testShouldThrowBadRequestExceptionCauseExceededTransferLimit() {
        var amount = sender.dailyTransferLimit().add(BigDecimal.TEN);

        BadRequestException exception = assertThrows(
            BadRequestException.class,
            () -> transferBankAccount.invoke(sender.id(), receiver.id(), amount)
        );

        assertEquals(
            ErrorCode.EXCEEDED_TRANSFER_LIMIT,
            exception.getCode()
        );

        assertEquals(
            String.format("Today's transfer(%s) limit has been exceeded", sender.dailyTransferLimit()),
            exception.getMessage()
        );
    }
}
