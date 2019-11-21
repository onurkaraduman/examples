package com.onrkrdmn;

import com.onrkrdmn.cache.AutoRefreshCache;
import com.onrkrdmn.cache.Cache;
import com.onrkrdmn.cache.model.Cacheable;
import com.onrkrdmn.cache.model.CachedObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class App {
    private static AtomicInteger tmpCount = new AtomicInteger(0);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {

        Map<Object, Cacheable> map = new HashMap<>();
        AutoRefreshCache aCache = new AutoRefreshCache(() -> {
            System.out.println("Update cache service is called");
            int index = tmpCount.getAndIncrement();
            String key = "Key:" + index;
            String value = "Value:" + index;
            Cacheable cacheable = new CachedObject(key, value);
            map.put(key, cacheable);
            return map;
        }, 5);


        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("printCache is running");
            printCache(aCache);
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void printCache(Cache cache) {
        List<Cacheable> allValues = cache.getAllValues();
        for (Cacheable value : allValues) {
            System.out.println(value.getIdentifier() + " - " + value.getObject());
        }
    }
}
