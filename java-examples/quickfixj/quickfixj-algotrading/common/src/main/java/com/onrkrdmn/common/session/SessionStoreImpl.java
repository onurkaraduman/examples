package com.onrkrdmn.common.session;

import quickfix.Session;
import quickfix.SessionID;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStoreImpl implements SessionStore {

    private Map<SessionID, Session> sessions = new ConcurrentHashMap<>();

    @Override
    public Session getSession(SessionID sessionID) {
        return sessions.get(sessionID);
    }

    @Override
    public Map<SessionID, Session> getSessions() {
        return sessions;
    }

    @Override
    public void addSession(SessionID sessionID, Session session) {
        sessions.put(sessionID, session);
    }

    @Override
    public void removeSession(SessionID sessionID) {
        sessions.remove(sessionID);
    }
}
