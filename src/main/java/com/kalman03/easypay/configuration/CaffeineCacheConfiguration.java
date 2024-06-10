package com.kalman03.easypay.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CaffeineCacheConfiguration {

	@Bean(name = "oneHourCacheManager")
	CacheManager oneHourCacheManager() {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder().initialCapacity(50) // 初始大小
				.maximumSize(51) // 最大大小
				.expireAfterWrite(60, TimeUnit.MINUTES); // 写入/更新之后1小时过期

		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setAllowNullValues(true);
		caffeineCacheManager.setCaffeine(caffeine);
		return caffeineCacheManager;
	}
}
