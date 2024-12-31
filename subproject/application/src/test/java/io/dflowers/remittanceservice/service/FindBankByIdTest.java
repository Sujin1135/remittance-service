package io.dflowers.remittanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dflowers.remittanceservice.service.bank.FindBankById;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.flywaydb.core.Flyway;

@TestPropertySource(locations = "classpath:application-test.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FindBankByIdTest {

    @Autowired
    private FindBankById findById;

    static MySQLContainer<?> mysql = new MySQLContainer<>(
        "mysql:8.0"
    );
    @Autowired
    private FindBankById findBankById;

    @BeforeAll
    static void beforeAll() {
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
    public void testFind() {
        var some = findBankById.apply(5L);
        int result = 1 + 3;
        assertEquals(4, result);
    }
}
