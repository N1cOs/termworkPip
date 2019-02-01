package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.CredentialDto;

public interface ChangeCredentialsService {
    void changeEmail(int userId, CredentialDto credential);

    void changePassword(int userId, CredentialDto credential);
}