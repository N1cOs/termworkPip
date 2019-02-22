package ru.ifmo.se.termwork.config;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class XMPPConfig {

    @Autowired
    private Environment environment;

    @Bean
    public AccountManager accountManager() throws Exception {
        AccountManager accountManager = AccountManager.getInstance(xmppConnection());
        accountManager.sensitiveOperationOverInsecureConnection(true);
        return accountManager;
    }

    private AbstractXMPPConnection xmppConnection() throws Exception {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost(environment.getProperty("xmpp.host"))
                .setPort(environment.getProperty("xmpp.port", Integer.class))
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setUsernameAndPassword(
                        environment.getProperty("xmpp.user"),
                        environment.getProperty("xmpp.password")
                )
                .setXmppDomain(environment.getProperty("xmpp.domain"))
                .build();
        XMPPTCPConnection connection = new XMPPTCPConnection(config);
        connection.setReplyTimeout(10000);
        connection.connect().login();
        return connection;
    }
}
