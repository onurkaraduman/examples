package com.onrkrdmn.common.app;

import com.onrkrdmn.common.QuickFixJUtils;
import com.onrkrdmn.common.session.SessionStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quickfix.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixApplicationImpl implements FixApplication {

    private static final Logger LOGGER = LogManager.getLogger(FixApplicationImpl.class);

    private final SessionStore sessionStore;
    // key: reqId
    private final Map<String, FixMessageHandler> fixMessageHandlers = new ConcurrentHashMap<>();


    protected FixApplicationImpl(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void onCreate(SessionID sessionID) {

    }

    @Override
    public void onLogon(SessionID sessionID) {
        sessionStore.addSession(sessionID, Session.lookupSession(sessionID));
    }

    @Override
    public void onLogout(SessionID sessionID) {
        sessionStore.removeSession(sessionID);
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
        String reqId = QuickFixJUtils.getReqId(message);
        FixMessageHandler handler = fixMessageHandlers.get(reqId);
        if (handler != null) {
            handler.handle(message);
        } else {
            LOGGER.info("Couldn't be found dispatcher for:" + sessionID);
        }
    }


    @Override
    public Map<SessionID, Session> getLoggedSessions() {
        return sessionStore.getSessions();
    }

    /**
     * it sends to first session
     *
     * @param message
     */
    @Override
    public void send(Message message) {
        if (!sessionStore.getSessions().isEmpty()) {
            Session session = sessionStore.getSessions().entrySet().iterator().next().getValue();
            session.send(message);
        }
    }

    @Override
    public void registerFixMessageHandler(String reqId, FixMessageHandler fixMessageHandler) {
        fixMessageHandlers.put(reqId, fixMessageHandler);
    }

    public static interface FixMessageHandler {
        public void handle(Message message);
    }
}
