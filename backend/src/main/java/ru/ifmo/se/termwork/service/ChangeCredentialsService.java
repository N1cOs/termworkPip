package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.CredentialDTO;

public interface ChangeCredentialsService {
    void changeEmail(int userId, CredentialDTO credential);

    void changePassword(int userId, CredentialDTO credential);
}