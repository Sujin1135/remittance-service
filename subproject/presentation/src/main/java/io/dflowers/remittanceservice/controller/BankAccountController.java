package io.dflowers.remittanceservice.controller;

import io.dflowers.remittanceservice.dto.CreateAccountRequest;
import io.dflowers.remittanceservice.dto.CreateAccountResponse;
import io.dflowers.remittanceservice.dto.ErrorResponse;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
    })
    CreateAccountResponse createAccount(
        @Valid @RequestBody CreateAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new CreateAccountResponse(createBankAccount.invoke(body.toDomain()));
    }
}
