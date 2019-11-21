package com.onrkrdmn.cache;


import com.onrkrdmn.cache.model.Cacheable;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoRefreshCache extends SimpleCache {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private AutoRefreshCacheService autoRefreshCacheService;

    private int autoRefreshIntervalSEC = 15; //second

    public AutoRefreshCache(AutoRefreshCacheService autoRefreshCacheService, int autoRefreshIntervalSEC) {
        this(autoRefreshCacheService);
        this.autoRefreshIntervalSEC = autoRefreshIntervalSEC;
        init();
    }

    public AutoRefreshCache(AutoRefreshCacheService autoRefreshCacheService) {
        if (autoRefreshCacheService == null) {
            new RuntimeException("AutorefreshCacheService cannot be null");
        }
        this.autoRefreshCacheService = autoRefreshCacheService;
        init();
    }

    public void init() {
        Runnable runnable = () -> {
            Map<Object, Cacheable> updates = autoRefreshCacheService.getUpdate();
            if (updates != null && !updates.isEmpty()) {
                cacheHashMap.clear();
                cacheHashMap.putAll(updates);
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable, autoRefreshIntervalSEC, autoRefreshIntervalSEC, TimeUnit.SECONDS);
    }

    public interface AutoRefreshCacheService {
        Map<Object, Cacheable> getUpdate();
    }
}
