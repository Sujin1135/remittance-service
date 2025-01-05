package io.dflowers.remittanceservice.controller;

import io.dflowers.remittanceservice.domain.BankTransaction;
import io.dflowers.remittanceservice.dto.CreateAccountRequest;
import io.dflowers.remittanceservice.dto.BankAccountResponse;
import io.dflowers.remittanceservice.dto.DeleteAccountRequest;
import io.dflowers.remittanceservice.dto.DepositBankAccountRequest;
import io.dflowers.remittanceservice.dto.ErrorResponse;
import io.dflowers.remittanceservice.dto.TransferBankAccountRequest;
import io.dflowers.remittanceservice.dto.WithdrawBankAccountRequest;
import io.dflowers.remittanceservice.service.CreateBankAccount;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.NotFoundException;
import io.dflowers.remittanceservice.service.DeleteBankAccount;
import io.dflowers.remittanceservice.service.DepositBankAccount;
import io.dflowers.remittanceservice.service.FindTransactionsByAccountId;
import io.dflowers.remittanceservice.service.TransferBankAccount;
import io.dflowers.remittanceservice.service.WithdrawBankAccount;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final CreateBankAccount createBankAccount;
    private final DeleteBankAccount deleteBankAccount;
    private final WithdrawBankAccount withdrawBankAccount;
    private final DepositBankAccount depositBankAccount;
    private final TransferBankAccount transferBankAccount;
    private final FindTransactionsByAccountId findTransactionsByAccountId;

    @PostMapping
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
    BankAccountResponse createAccount(
        @Valid @RequestBody CreateAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new BankAccountResponse(createBankAccount.invoke(body.toDomain()));
    }

    @DeleteMapping("/{id}")
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

    @PostMapping("/{id}/withdraw")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "400",
            description = "계좌 잔액이 출금액보다 적을 경우 / 일 출금 한도를 넘긴 경우 / 요청값이 벨리데이션 규칙에 어긋날 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
        @ApiResponse(
            responseCode = "404",
            description = "요청한 계좌 ID 가 없는 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
    })
    public BankAccountResponse withdraw(
        @PathVariable("id") Long id,
        @Valid @RequestBody WithdrawBankAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new BankAccountResponse(
            withdrawBankAccount.invoke(id, body.getAmount())
        );
    }

    @PostMapping("/{id}/deposit")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "400",
            description = "요청값이 벨리데이션 규칙에 어긋날 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
        @ApiResponse(
            responseCode = "404",
            description = "요청한 계좌 ID 가 없는 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
    })
    public BankAccountResponse deposit(
        @PathVariable("id") Long id,
        @Valid @RequestBody DepositBankAccountRequest body
    ) throws NotFoundException {
        return new BankAccountResponse(
            depositBankAccount.invoke(id, body.getAmount())
        );
    }

    @PostMapping("/{id}/transfer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(
            responseCode = "400",
            description = "요청값이 벨리데이션 규칙에 어긋날 경우 / 일 이체 한도가 초과한 경우 / 계좌 잔액이 부족한 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
        @ApiResponse(
            responseCode = "404",
            description = "요청한 계좌(보내는 사람 / 받는사람) ID 가 없는 경우",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )),
    })
    public BankAccountResponse transfer(
        @PathVariable("id") Long id,
        @Valid @RequestBody TransferBankAccountRequest body
    ) throws NotFoundException, BadRequestException {
        return new BankAccountResponse(
            transferBankAccount.invoke(id, body.getReceiverId(), body.getAmount())
        );
    }

    @GetMapping("/{id}/transactions")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
    public List<BankTransaction> getTransactions(
        @PathVariable("id") long id,
        @RequestParam(name = "cursor")
        @Parameter(description = "커서 transaction id", example = "0")
        long cursor
    ) {
        return findTransactionsByAccountId.invoke(id, cursor);
    }
}
