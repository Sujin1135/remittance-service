package io.dflowers.remittanceservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "등록할 계좌 정보")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAccountRequest {
    @Schema(description = "고객 ID")
    @NotNull(message = "{field.not.null}")
    long userId;

    @Schema(description = "통장 이름")
    @NotNull(message = "{field.not.null}")
    @NotBlank(message = "{field.not.null}")
    String name;

    @Schema(description = "은행 종류")
    @NotNull(message = "{field.not.null}")
    Bank bank;

    @Schema(description = "계좌번호")
    @Size(min = 10, message = "{field.size.too_short}")
    @Size(max = 20, message = "{field.size.too_long}")
    String accountNumber;

    public BankAccount toDomain() {
        return BankAccount.of(
            userId,
            name,
            bank,
            accountNumber,
            BigDecimal.ZERO
        );
    }
}
