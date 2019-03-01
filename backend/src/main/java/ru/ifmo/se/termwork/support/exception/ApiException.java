package ru.ifmo.se.termwork.support.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    private String message;

    private Object[] messageArgs;

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
}
