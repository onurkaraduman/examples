package com.onrkrdmn.cache.model;

import java.util.Date;

public class CachedObject implements Cacheable {

    private Date dateOfExpiration;
    private Object identifier;
    private Object object;

    public CachedObject(Object identifier, Object object) {
        this.identifier = identifier;
        this.object = object;
    }

    @Override
    public boolean isExpired() {
        if (dateOfExpiration != null) {
            return dateOfExpiration.before(new Date());
        }
        // lives forever
        else {
            return false;
        }
    }

    @Override
    public Object getIdentifier() {
        return identifier;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
