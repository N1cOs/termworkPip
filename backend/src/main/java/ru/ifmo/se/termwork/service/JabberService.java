package ru.ifmo.se.termwork.service;

import java.util.Map;

public interface JabberService {

    void signUp(String username, String password, Map<String, String> attributes);

    /**
     *
     * @param username username or email of user
     * @param body body for the message
     */
    void sendMessage(String username, String body);
}
