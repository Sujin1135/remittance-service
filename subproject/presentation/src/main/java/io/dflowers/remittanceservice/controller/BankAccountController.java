package io.dflowers.remittanceservice.controller;

import io.dflowers.remittanceservice.dto.CreateAccountRequest;
import io.dflowers.remittanceservice.dto.CreateAccountResponse;
import io.dflowers.remittanceservice.dto.DeleteAccountRequest;
import io.dflowers.remittanceservice.dto.ErrorResponse;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.service.bank.DeleteBankAccount;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final CreateBankAccount createBankAccount;
    private final DeleteBankAccount deleteBankAccount;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "성공"),
        @ApiResponse(responseCode = "400", description = "요청값이 올바르지 않을 경우", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "404", description = "고객 ID를 찾을 수 없을 경우", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
    })
    CreateAccountResponse createAccount(
        @Valid @RequestBody CreateAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new CreateAccountResponse(createBankAccount.invoke(body.toDomain()));
    }

    @DeleteMapping("/accounts/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "이미 삭제된 계좌 ID를 다시 삭제 요청한 경우", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "404", description = "계좌 ID or 고객 ID 를 찾을 수 없을 경우", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )),
    })
    public void deleteAccount(
        @PathVariable("id") Long id,
        @Valid @RequestBody DeleteAccountRequest body
    ) throws NotFoundException, BadRequestException {
        deleteBankAccount.invoke(id, body.getUserId());
    }
}
