package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.service.MessageService;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    @Qualifier("apiMessages")
    private MessageSource apiMessageSource;

    @Autowired
    @Qualifier("clientMessages")
    private MessageSource clientMessageSource;

    @Override
    public String getApiMessage(String code) {
        return apiMessageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Override
    public String getApiMessage(String code, Object[] args) {
        return apiMessageSource.getMessage(code, args, Locale.ENGLISH);
    }

    @Override
    public String getClientMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return clientMessageSource.getMessage(code, null, locale);
    }

    @Override
    public String getClientMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return clientMessageSource.getMessage(code, args, locale);
    }
}
