package io.dflowers.remittanceservice.dto;

import io.dflowers.remittanceservice.service.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Getter;

@Schema(description = "에러 응답 객체")
@Getter
public class ErrorResponse {
    @Schema(description = "에러 발생일시")
    OffsetDateTime timestamp;
    @Schema(description = "에러 코드")
    ErrorCode code;
    @Schema(description = "에러 메시지")
    String message;

    public ErrorResponse(ErrorCode code, String message) {
        this.timestamp = OffsetDateTime.now();
        this.code = code;
        this.message = message;
    }
}
