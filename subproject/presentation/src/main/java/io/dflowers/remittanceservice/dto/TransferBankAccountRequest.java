package io.dflowers.remittanceservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class TransferBankAccountRequest {
    @NotNull(message = "{field.not.null}")
    long receiverId;

    @NotNull(message = "{field.not.null}")
    @Min(value = 0, message = "{field.withdraw.min}")
    BigDecimal amount;
}
