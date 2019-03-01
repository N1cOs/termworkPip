package ru.ifmo.se.termwork.support.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ClientException extends RuntimeException {

    private final HttpStatus status;

    private String message;

    private Object[] messageArgs;

    private List<InputError> inputErrors;

    public ClientException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ClientException(HttpStatus status, String message, Object[] args) {
        super(message);
        this.status = status;
        this.message = message;
        this.messageArgs = args;
    }

    public ClientException(HttpStatus status, InputError... inputError){
        this.status = status;
        this.inputErrors = Arrays.asList(inputError);
    }

    public ClientException(HttpStatus status, List<InputError> inputErrors) {
        this.status = status;
        this.inputErrors = inputErrors;
    }
}
