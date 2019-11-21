package com.onrkrdmn.cache;

import com.onrkrdmn.cache.model.Cacheable;

import java.util.List;

public interface Cache {

    void putCache(Cacheable cacheable);

    Cacheable getCache(Object identifier);

    boolean isExisting(Object identifier);

    int size();

    boolean isEmpty();

    List<Cacheable> getAllValues();

    List<Object> getAllKeys();
}
