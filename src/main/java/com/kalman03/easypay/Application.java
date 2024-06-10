package com.kalman03.easypay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.kalman03.common.logs.EnableLoggingMDCFilter;
import com.kalman03.common.redis.EnableRedis;
import com.kalman03.common.weixin.EnableWeixin;
import com.kalman03.springboot.velocity.VelocityAutoConfiguration;

@EnableLoggingMDCFilter
@EnableRedis
@EnableScheduling
@SpringBootApplication
@Import(VelocityAutoConfiguration.class)
@EnableWeixin
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
