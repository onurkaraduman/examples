package com.onrkrdmn.server.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance1")
                .setGroupConfig(new GroupConfig("hazelcastexample"))
                .addMapConfig(new MapConfig()
                        .setName("configuration")
                        .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(-1))
                .setManagementCenterConfig(new ManagementCenterConfig().setUrl("http://localhost:8080/hazelcast-mancenter").setEnabled(true));
        return config;
    }
}
