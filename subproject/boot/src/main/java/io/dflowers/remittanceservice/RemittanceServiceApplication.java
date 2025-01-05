package io.dflowers.remittanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemittanceServiceApplication {

    public static void main(String[] args) {
        System.setProperty(
            "spring.config.name",
            "application,application-infrastructure,application-app,application-presentation,application-domain"
        );
        SpringApplication.run(RemittanceServiceApplication.class, args);
    }

}
