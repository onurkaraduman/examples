package com.onrkrdmn.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration(exclude = {ServletWebServerFactoryAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class HazelcastServerApp {
    public static void main(String[] args) {
        SpringApplication.run(HazelcastServerApp.class, args);
    }
}
