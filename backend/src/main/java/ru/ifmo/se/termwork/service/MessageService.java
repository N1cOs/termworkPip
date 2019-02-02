package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.ExceptionDto;

public interface MessageService {
    String getMessage(String code);

    String getMessage(String code, Object[] args);

    ExceptionDto getAsExceptionDto(String code);

    ExceptionDto getAsExceptionDto(String code, Object[] args);
}
