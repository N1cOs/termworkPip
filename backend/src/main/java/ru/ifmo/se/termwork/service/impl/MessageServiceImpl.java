package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.dto.ExceptionDto;
import ru.ifmo.se.termwork.service.MessageService;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    @Override
    public String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public ExceptionDto getAsExceptionDto(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return new ExceptionDto(messageSource.getMessage(code, null, locale));
    }

    @Override
    public ExceptionDto getAsExceptionDto(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return new ExceptionDto(messageSource.getMessage(code, args, locale));
    }
}
