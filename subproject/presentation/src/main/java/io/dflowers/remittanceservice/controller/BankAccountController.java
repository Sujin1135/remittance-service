package io.dflowers.remittanceservice.controller;

import io.dflowers.remittanceservice.dto.CreateAccountRequest;
import io.dflowers.remittanceservice.dto.CreateAccountResponse;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
import io.dflowers.remittanceservice.service.exception.BadRequestException;
import io.dflowers.remittanceservice.service.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final CreateBankAccount createBankAccount;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    CreateAccountResponse createAccount(
        @Valid @RequestBody CreateAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new CreateAccountResponse(createBankAccount.invoke(body.toDomain()));
    }
}
