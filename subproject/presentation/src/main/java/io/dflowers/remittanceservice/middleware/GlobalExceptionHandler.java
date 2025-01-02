package io.dflowers.remittanceservice.middleware;

import io.dflowers.remittanceservice.dto.ErrorResponse;
import io.dflowers.remittanceservice.exception.BadRequestException;
import io.dflowers.remittanceservice.exception.ErrorCode;
import io.dflowers.remittanceservice.exception.NotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotfoundException(NotFoundException e) {
        return new ResponseEntity<>(
            new ErrorResponse(e.getCode(), e.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(
            new ErrorResponse(e.getCode(), e.getMessage()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        Map<String, String> fieldErrors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage
            ));

        return new ResponseEntity<>(
            new ErrorResponse(ErrorCode.NOT_VALID, fieldErrors.toString()),
            HttpStatus.BAD_REQUEST
        );
    }
}
