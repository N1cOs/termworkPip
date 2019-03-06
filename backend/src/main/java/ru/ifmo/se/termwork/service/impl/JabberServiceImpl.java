package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Speciality;
import ru.ifmo.se.termwork.service.JabberService;
import ru.ifmo.se.termwork.service.MessageService;

import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor
public class JabberServiceImpl implements JabberService {

    private final AccountManager accountManager;

    private final ChatManager chatManager;

    private final MessageService messageService;

    @Value("${xmpp.domain}")
    private String domain;

    @Async
    @Override
    public void signUp(String username, String password, Map<String, String> attributes) {
        Localpart login = Localpart.fromOrNull(username);
        try {
            accountManager.createAccount(login, password, attributes);
            sendMessage(username, messageService.getClientMessage("jabber.bot.signUp"));
        } catch (Exception e) {
            log.error("Jabber Exception", e);
        }
    }

    @Override
    public void sendMessage(String username, String body) {
        if(username.indexOf('@') != -1){
            username = username.substring(0, username.lastIndexOf('@'));
        }
        try {
            EntityBareJid jid = JidCreate.entityBareFrom(username + "@" + domain);
            Chat chat = chatManager.chatWith(jid);
            chat.send(body);
        } catch (Exception e) {
            log.error("Jabber bot exception", e);
        }
    }

    @Override
    public void notifyStudents(Speciality speciality) {

    }
}
