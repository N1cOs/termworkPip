package ru.ifmo.se.termwork.service;

public interface MessageService {
    String getMessage(String code);

    String getMessage(String code, Object[] args);
}
