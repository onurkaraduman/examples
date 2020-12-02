package com.onrkrdmn.common;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionFactory;
import quickfix.field.OrderID;

public class QuickFixJUtils {

    public static boolean isAcceptor(String connectionType) {
        return SessionFactory.ACCEPTOR_CONNECTION_TYPE.equals(connectionType);
    }

    public static boolean isInitiator(String connectionType) {
        return SessionFactory.INITIATOR_CONNECTION_TYPE.equals(connectionType);
    }

    public static String getReqId(Message message) {
        try {
            return message.getString(OrderID.FIELD);
        } catch (FieldNotFound fieldNotFound) {
            throw new RuntimeException(fieldNotFound);
        }
    }
}
