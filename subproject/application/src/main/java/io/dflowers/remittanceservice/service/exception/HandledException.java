package io.dflowers.remittanceservice.service.exception;

import lombok.Getter;

@Getter
public class HandledException extends RuntimeException {
    ErrorCode code;

    public HandledException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
