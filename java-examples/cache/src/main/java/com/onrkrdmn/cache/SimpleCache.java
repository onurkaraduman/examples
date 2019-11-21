package com.onrkrdmn.cache;


import com.onrkrdmn.cache.model.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SimpleCache implements Cache {

    protected Map<Object, Cacheable> cacheHashMap = new ConcurrentHashMap<>();

    @Override
    public int size() {
        return cacheHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return cacheHashMap.isEmpty();
    }

    @Override
    public boolean isExisting(Object identifier) {
        return cacheHashMap.containsKey(identifier);
    }

    @Override
    public void putCache(Cacheable cacheable) {
        cacheHashMap.put(cacheable.getIdentifier(), cacheable);
    }

    @Override
    public Cacheable getCache(Object identifier) {
        return cacheHashMap.get(identifier);
    }

    @Override
    public List<Cacheable> getAllValues() {
        return new ArrayList<>(cacheHashMap.values());
    }

    @Override
    public List<Object> getAllKeys() {
        return new ArrayList<>(cacheHashMap.keySet());
    }
}
