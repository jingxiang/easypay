package com.kalman03.easypay.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableRedisHttpSession
public class SpringSessionConfiguration {

	@Bean
	HttpSessionIdResolver httpSessionIdResolver() {
		return new CookieHttpSessionIdResolver();
	}

}
