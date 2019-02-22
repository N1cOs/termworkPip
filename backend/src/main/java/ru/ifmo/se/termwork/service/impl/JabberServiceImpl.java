package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.controller.exception.ApiException;
import ru.ifmo.se.termwork.service.JabberService;

import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor
public class JabberServiceImpl implements JabberService {

    private final AccountManager accountManager;

    @Async
    @Override
    public void signUp(String username, String password, Map<String, String> attributes) {
        Localpart login = Localpart.fromOrNull(username);
        try {
            accountManager.createAccount(login, password, attributes);
        } catch (Exception e) {
            log.error("Jabber Exception", e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "exception.xmpp.connection");
        }
    }
}
