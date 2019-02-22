package ru.ifmo.se.termwork.controller.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    private String message;

    private Object[] messageArgs;

    private List<InputError> inputErrors;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status, String message, Object[] args) {
        super(message);
        this.status = status;
        this.message = message;
        this.messageArgs = args;
    }

    public ApiException(HttpStatus status, List<InputError> inputErrors) {
        this.status = status;
        this.inputErrors = inputErrors;
    }
}
