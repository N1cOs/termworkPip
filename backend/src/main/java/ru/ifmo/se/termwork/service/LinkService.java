package ru.ifmo.se.termwork.service;

public interface LinkService {

    void generateWorkerSignUpLink(int headWorkerId, String workerEmail);

    boolean isValid(String uuid);

    void removeInvalidLinks();
}
