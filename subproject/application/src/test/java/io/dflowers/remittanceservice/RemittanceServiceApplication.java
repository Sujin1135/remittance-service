package io.dflowers.remittanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemittanceServiceApplication {

    public static void main(String[] args) {
        System.setProperty(
            "spring.config.name",
            "application-test"
        );
        SpringApplication.run(RemittanceServiceApplication.class, args);
    }

}
