package com.onrkrdmn.common.connector;

import com.onrkrdmn.common.QuickFixJUtils;
import quickfix.*;

import java.io.InputStream;

public class ConnectorFactory {

    public static Connector createConnector(Application fixApp, InputStream config) throws ConfigError {
        SessionSettings settings = new SessionSettings(config);
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);

        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        String connectionType = settings.getString(SessionFactory.SETTING_CONNECTION_TYPE);
        if (QuickFixJUtils.isAcceptor(connectionType)) {
            return new SocketAcceptor(fixApp, storeFactory, settings, logFactory, messageFactory);
        } else if (QuickFixJUtils.isInitiator(connectionType)) {
            return new SocketInitiator(fixApp, storeFactory, settings, logFactory, messageFactory);
        }
        throw new IllegalArgumentException(String.format("connectionType(%s) is not supported", connectionType));
    }
}
