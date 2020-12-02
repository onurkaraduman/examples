package com.onrkrdmn.common.app;

import com.onrkrdmn.common.session.SessionStoreImpl;
import quickfix.Application;

public class FixApplicationFactory {

    public static FixApplication createFixApplication() {
        return new FixApplicationImpl(new SessionStoreImpl());
    }
}
