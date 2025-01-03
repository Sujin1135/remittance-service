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
import io.dflowers.remittanceservice.service.bank.DeleteBankAccount;
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
public class DeleteBankAccountTest {

    @Autowired
    private DeleteBankAccount deleteBankAccount;

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
        var user = userRepository.save(UserDataFactory.generate("최민규"));
        bankAccount = bankAccountRepository.save(BankAccountDataFactory.generate(user.id()));
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
    public void testShouldReturnVoid() throws NotFoundException, BadRequestException {
        deleteBankAccount.invoke(bankAccount.id(), bankAccount.userId());
    }

    @Test
    public void testShouldThrowBadRequestExceptionCauseAlreadyDeleted() throws NotFoundException, BadRequestException {
        deleteBankAccount.invoke(bankAccount.id(), bankAccount.userId());

        Exception exception = assertThrows(
            BadRequestException.class,
            () -> deleteBankAccount.invoke(bankAccount.id(), bankAccount.userId())
        );

        assertEquals(String.format("This bank account was deleted by id(%d)", bankAccount.id()), exception.getMessage());
    }

    @Test
    public void testShouldThrowNotFoundExceptionCauseInvalidId() {
        var invalidId = 9999999;

        Exception exception = assertThrows(
            NotFoundException.class,
            () -> deleteBankAccount.invoke(invalidId, bankAccount.userId())
        );

        assertEquals(String.format("Bank account is not found by id(%d) and userId(%d)", invalidId, bankAccount.userId()), exception.getMessage());
    }

    @Test
    public void testShouldThrowNotFoundExceptionCauseInvalidUserId() {
        var invalidId = 9999999;

        Exception exception = assertThrows(
            NotFoundException.class,
            () -> deleteBankAccount.invoke(bankAccount.id(), invalidId)
        );

        assertEquals(String.format("Bank account is not found by id(%d) and userId(%d)", bankAccount.id(), invalidId), exception.getMessage());
    }
}
