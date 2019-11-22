package com.onrkrdmn.springbootcertauth.restapi.security;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockUserStore {

    private Map<String, String> users = new ConcurrentHashMap<>();


    @PostConstruct
    public void init() {
        users.put("client1", "onur");
        users.put("client2", "onur2");
    }

    public String getUser(String key) {
        return users.get(key);
    }
}
