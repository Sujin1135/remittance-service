package io.dflowers.remittanceservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "계좌 등록 후 응답 정보")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAccountResponse {
    @Schema(description = "계좌 ID")
    long id;

    @Schema(description = "고객 ID")
    long userId;

    @Schema(description = "계좌명")
    String name;

    @Schema(description = "은행 종류")
    Bank bank;

    @Schema(description = "계좌번호")
    String accountNumber;

    @Schema(description = "잔고")
    BigDecimal balance;

    @Schema(description = "생성일")
    OffsetDateTime created;

    @Schema(description = "수정일")
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
