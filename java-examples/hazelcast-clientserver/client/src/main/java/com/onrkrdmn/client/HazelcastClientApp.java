package com.onrkrdmn.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HazelcastClientApp {
    public static void main(String[] args) {
        SpringApplication.run(HazelcastClientApp.class, args);
    }
}
