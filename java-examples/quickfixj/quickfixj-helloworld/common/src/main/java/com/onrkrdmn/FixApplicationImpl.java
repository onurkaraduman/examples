package com.onrkrdmn;

import org.apache.log4j.Logger;
import quickfix.*;
import quickfix.field.Text;
import quickfix.fix44.QuoteRequest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixApplicationImpl implements Application {
    private static final Logger LOGGER = Logger.getLogger(FixApplicationImpl.class);
    private Session defaultSession;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public FixApplicationImpl() {
        scheduledExecutorService.scheduleAtFixedRate(() -> sendHelloWorld(), 5, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onCreate(SessionID sessionID) {

    }

    @Override
    public void onLogon(SessionID sessionID) {
        LOGGER.info("Session is logged on");
        defaultSession = Session.lookupSession(sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {

    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        LOGGER.info("Message is being sent:" + message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        LOGGER.info("Message received:" + message);
    }

    private void sendHelloWorld() {
        if (isLoggedOn()) {
            QuoteRequest quoteRequest = new QuoteRequest();
            quoteRequest.setString(Text.FIELD, "Hello World");
            defaultSession.send(quoteRequest);
        } else {
            LOGGER.info("session is not logged in, message will not send");
        }
    }

    private boolean isLoggedOn() {
        return defaultSession != null && defaultSession.isLoggedOn();
    }
}
