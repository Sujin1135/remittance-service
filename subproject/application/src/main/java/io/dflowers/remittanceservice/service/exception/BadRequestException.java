package io.dflowers.remittanceservice.service.exception;

public class BadRequestException extends HandledException {

    public BadRequestException(ErrorCode code, String message) {
        super(code, message);
    }

    public BadRequestException(String message) {
        super(ErrorCode.ALREADY_REGISTERED, message);
    }
}
