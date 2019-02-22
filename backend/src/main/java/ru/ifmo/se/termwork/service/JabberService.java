package ru.ifmo.se.termwork.service;

import java.util.Map;

public interface JabberService {

    void signUp(String username, String password, Map<String, String> attributes);
}
