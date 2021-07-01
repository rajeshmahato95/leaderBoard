package com.game.TopScoreService.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean(name="user-cache-config")
    public Caffeine<Object,Object> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Primary
    @Bean(name="user-cache")
    public CacheManager cacheManager(@Qualifier("user-cache-config") Caffeine<Object,Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("user-cache");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
