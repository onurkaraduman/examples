package com.onrkrdmn.common.app;

import quickfix.Application;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;

import java.util.Map;

public interface FixApplication extends Application {
    public Map<SessionID, Session> getLoggedSessions();

    public void send(Message message);

    public void registerFixMessageHandler(String reqId, FixApplicationImpl.FixMessageHandler fixMessageHandler);
}
