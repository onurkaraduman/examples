package com.onrkrdmn.provider;

import com.onrkrdmn.common.app.FixApplication;
import com.onrkrdmn.common.app.FixApplicationFactory;
import com.onrkrdmn.common.connector.ConnectorFactory;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.Connector;

public class ProviderStarter {
    public static void main(String[] args) throws ConfigError {
        FixApplication application = FixApplicationFactory.createFixApplication();
        Connector connector = ConnectorFactory.createConnector(
                application,
                ProviderStarter.class.getClassLoader().getResourceAsStream("fixsession_initiator.conf"));
        connector.start();
    }
}
