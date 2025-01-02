package io.dflowers.remittanceservice.service.exception;

public class NotFoundException extends HandledException {

  public NotFoundException(ErrorCode code, String message) {
    super(code, message);
  }

  public NotFoundException(String message) {
    super(ErrorCode.NOT_FOUND, message);
  }
}
