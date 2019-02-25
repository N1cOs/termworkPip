package ru.ifmo.se.termwork.service;

public interface LinkService {

    String generateLink();

    boolean isValid(String uuid);

    void destroyLink(String link);

    void removeInvalidLinks();
}
