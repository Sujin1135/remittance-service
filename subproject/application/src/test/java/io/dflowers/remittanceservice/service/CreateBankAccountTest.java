package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dflowers.remittanceservice.domain.User;
import io.dflowers.remittanceservice.factory.BankAccountDataFactory;
import io.dflowers.remittanceservice.factory.UserDataFactory;
import io.dflowers.remittanceservice.repository.UserRepository;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
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
public class CreateBankAccountTest {

    @Autowired
    private CreateBankAccount createBankAccount;

    @Autowired
    private UserRepository userRepository;

    private User user;

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
        user = userRepository.save(UserDataFactory.generate("최민규"));
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
    public void testShouldBeReturnByInput() {
        var params = BankAccountDataFactory.generate(user.id());
        var sut = createBankAccount.apply(params);

        assertEquals(sut.userId(), params.userId());
        assertEquals(sut.accountNumber(), params.accountNumber());
        assertEquals(sut.name(), params.name());
    }
}
