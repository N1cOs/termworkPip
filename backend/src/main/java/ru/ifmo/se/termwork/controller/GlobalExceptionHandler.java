package ru.ifmo.se.termwork.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ifmo.se.termwork.service.MessageService;

@ControllerAdvice
@Log4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageService messageService;

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentials(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageService.getAsExceptionDto(e.getMessage()));
    }
}
