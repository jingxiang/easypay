package com.kalman03.easypay.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author kalman03
 * @since 2021-12-06
 */
@Configuration
@EnableAutoConfiguration
public class ThreadPoolConfiguration {

	@Bean("threadPoolTaskExecutor")
	ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		/**
		 * 核心线程会一直存活，及时没有任务需要执行 当线程数小于核心线程数时，即使有线程空闲，线程池也会优先创建新线程处理
		 * 设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭
		 */
		executor.setCorePoolSize(15);
		/**
		 * 最大线程数 当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务
		 * 当线程数=maxPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常
		 */
		executor.setMaxPoolSize(15);
		//
		/**
		 * 队列最大长度 >=mainExecutor.maxSize 任务队列容量（阻塞队列） 当核心线程数达到最大时，新任务会放在队列中排队等待执行
		 */
		executor.setQueueCapacity(30);
		/**
		 * 线程空闲时间 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
		 * 如果allowCoreThreadTimeout=true，则会直到线程数量=0
		 */
		executor.setKeepAliveSeconds(300);
		// 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略
		// ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}
}
