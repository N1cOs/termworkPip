package ru.ifmo.se.termwork.controller.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private final int status;

    private String message;

    private List<InputError> inputErrors;

    public ApiException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(int status, List<InputError> inputErrors) {
        this.status = status;
        this.inputErrors = inputErrors;
    }
}
