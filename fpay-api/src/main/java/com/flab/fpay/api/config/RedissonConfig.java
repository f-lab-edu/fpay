package com.flab.fpay.api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.redis.port}")
    private String REDIS_PORT;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient() {
        Config redisConfig = new Config();
        redisConfig.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX + REDIS_HOST + ":" + REDIS_PORT)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(5);
        return Redisson.create(redisConfig);
    }
}
