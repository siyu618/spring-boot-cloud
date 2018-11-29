package com.study.example.apollo.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author didi
 */
@ConfigurationProperties(prefix = "redis.cache")
@Getter
public class SampleRedisConfig {
    private int expireSeconds;
    private int commandTimeout;

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }
}