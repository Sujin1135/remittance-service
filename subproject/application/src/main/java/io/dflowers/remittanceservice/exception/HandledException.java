package io.dflowers.remittanceservice.exception;

import lombok.Getter;

@Getter
public class HandledException extends Exception {
    ErrorCode code;

    public HandledException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
