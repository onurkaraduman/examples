package com.onrkrdmn.requester;

import com.onrkrdmn.common.app.FixApplication;
import com.onrkrdmn.common.app.FixApplicationFactory;
import com.onrkrdmn.common.connector.ConnectorFactory;
import org.atdl4j.data.exception.FIXatdlFormatException;
import quickfix.ConfigError;
import quickfix.Connector;
import quickfix.Message;
import quickfix.field.OrderID;
import quickfix.fix44.QuoteRequest;

import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

public class RequesterStarter {
    public static void main(String[] args) throws ConfigError, FIXatdlFormatException {
        FixApplication fixApplication = initFixEngine();
        AlgoUI algoUI = initAlgoUI();
        algoUI.addSendListener(parameters -> {
            Message message = translateToFix(UUID.randomUUID().toString(), parameters);
            fixApplication.send(message);
        });

    }

    public static FixApplication initFixEngine() throws ConfigError {
        FixApplication application = FixApplicationFactory.createFixApplication();
        Connector connector = ConnectorFactory.createConnector(
                application,
                RequesterStarter.class.getClassLoader().getResourceAsStream("fixsession_acceptor.conf"));
        connector.start();
        return application;
    }

    public static AlgoUI initAlgoUI() throws FIXatdlFormatException {
        AlgoUI algoUI = new AlgoUI();
        algoUI.init();
        return algoUI;
    }

    public static Message translateToFix(String uuId, Map<BigInteger, String> parameters) {
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setString(OrderID.FIELD, uuId);
        for (BigInteger tag : parameters.keySet()) {
            quoteRequest.setString(tag.intValue(), parameters.get(tag));
        }

        return quoteRequest;
    }
}
