package io.dflowers.remittanceservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAccountResponse {
    long id;

    long userId;

    String name;

    Bank bank;

    String accountNumber;

    BigDecimal balance;

    OffsetDateTime created;

    OffsetDateTime modified;

    public CreateAccountResponse(BankAccount bankAccount) {
        this.id = bankAccount.id();
        this.userId = bankAccount.userId();
        this.name = bankAccount.name();
        this.bank = bankAccount.bank();
        this.accountNumber = bankAccount.accountNumber();
        this.balance = bankAccount.balance();
        this.created = bankAccount.created();
        this.modified = bankAccount.modified();
    }
}
