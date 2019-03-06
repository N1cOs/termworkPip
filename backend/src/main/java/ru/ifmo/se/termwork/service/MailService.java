package ru.ifmo.se.termwork.service;

public interface MailService {

    void sendMail(String email, String subject, String body);

    void sendMailAsync(String email, String subject, String body);
}
