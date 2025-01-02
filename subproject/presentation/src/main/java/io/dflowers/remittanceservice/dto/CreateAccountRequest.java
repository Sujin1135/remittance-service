package io.dflowers.remittanceservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class CreateAccountRequest {
    @NotNull(message = "{field.not.null}")
    long userId;

    @NotNull(message = "{field.not.null}")
    @NotBlank(message = "{field.not.null}")
    String name;

    @NotNull(message = "{field.not.null}")
    Bank bank;

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
