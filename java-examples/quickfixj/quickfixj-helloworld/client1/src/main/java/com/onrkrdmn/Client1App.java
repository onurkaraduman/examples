package com.onrkrdmn;


import quickfix.*;

import java.io.InputStream;

public class Client1App {
    public static void main(String[] args) throws ConfigError {
        Application fixApplication = new FixApplicationImpl();
        Connector connector = createConnector(fixApplication, Client1App.class.getClassLoader().getResourceAsStream("fixsession_acceptor.conf"));
        connector.start();
    }

    private static Connector createConnector(Application fixApp, InputStream acceptorConfig) throws ConfigError {

        SessionSettings settings = new SessionSettings(acceptorConfig);
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);

        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        return new SocketAcceptor(fixApp, storeFactory, settings, logFactory, messageFactory);
    }
}
