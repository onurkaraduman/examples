package com.onrkrdmn.common.session;

import quickfix.Session;
import quickfix.SessionID;

import java.util.Map;

public interface SessionStore {

    public Session getSession(SessionID sessionID);

    public Map<SessionID, Session> getSessions();

    public void addSession(SessionID sessionID, Session session);

    public void removeSession(SessionID sessionID);
}
