package io.dflowers.remittanceservice.controller;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.dto.CreateAccountRequest;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
import io.dflowers.remittanceservice.service.bank.FindBankById;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
    private final FindBankById findBankById;
    private final CreateBankAccount createBankAccount;

    public BankAccountController(
        FindBankById findBankById,
        CreateBankAccount createBankAccount
    ) {
        this.findBankById = findBankById;
        this.createBankAccount = createBankAccount;
    }

    @GetMapping("/accounts")
    BankAccount getAccounts() {
        return findBankById.apply(5L);
    }

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    BankAccount createAccount(@Valid @RequestBody CreateAccountRequest body) {
        return createBankAccount.apply(body.toDomain());
    }
}
