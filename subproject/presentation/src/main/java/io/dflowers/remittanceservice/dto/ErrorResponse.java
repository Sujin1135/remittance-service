package io.dflowers.remittanceservice.dto;

import io.dflowers.remittanceservice.exception.ErrorCode;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ErrorResponse {
    OffsetDateTime timestamp;
    ErrorCode code;
    String message;

    public ErrorResponse(ErrorCode code, String message) {
        this.timestamp = OffsetDateTime.now();
        this.code = code;
        this.message = message;
    }
}
