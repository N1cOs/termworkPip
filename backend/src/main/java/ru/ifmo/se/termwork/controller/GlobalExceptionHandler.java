package ru.ifmo.se.termwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ifmo.se.termwork.service.MessageService;
import ru.ifmo.se.termwork.support.exception.ApiException;
import ru.ifmo.se.termwork.support.exception.ClientException;
import ru.ifmo.se.termwork.support.exception.Error;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageService messageService;

    /*
    * Actually error messages are codes for messages from resources
    */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleApiExceptions(ApiException e){
        if(e.getMessage() != null)
            e.setMessage(messageService.getApiMessage(e.getMessage(), e.getMessageArgs()));

        Error error = new Error(e.getMessage(), null);
        return ResponseEntity.status(e.getStatus()).body(error);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity handleClientException(ClientException e){
        if(e.getMessage() != null)
            e.setMessage(messageService.getClientMessage(e.getMessage(), e.getMessageArgs()));
        if(e.getInputErrors() != null)
            e.getInputErrors().forEach(s -> s.setInfo(messageService.getClientMessage(s.getInfo())));

        Error error = new Error(e.getMessage(), e.getInputErrors());
        return ResponseEntity.status(e.getStatus()).body(error);
    }


}
