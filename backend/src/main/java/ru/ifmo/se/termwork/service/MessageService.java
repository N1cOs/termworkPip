package ru.ifmo.se.termwork.service;

public interface MessageService {

    String getApiMessage(String code);

    String getApiMessage(String code, Object... args);

    String getClientMessage(String code);

    String getClientMessage(String code, Object... args);
}
