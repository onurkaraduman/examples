package com.onrkrdmn.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Value("${app.hazelcast.server.host}")
    private String serverHost;

    public String getServerHost() {
        return serverHost;
    }
}
