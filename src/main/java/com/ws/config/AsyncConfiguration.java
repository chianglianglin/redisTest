package com.ws.config;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.ws.project.model.properties.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);

	@Autowired
	private ThreadPool threadPool;

	@Bean(name = "sendJobExecutor")
	public Executor sendJobExecutor() {
		LOGGER.debug("Send To Channel Executor");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		/** 核心线程数 */
		executor.setCorePoolSize(threadPool.getSendJob().getCorePoolSize());
		/** 最大线程数 */
		executor.setMaxPoolSize(threadPool.getSendJob().getMaxPoolSize());
		/** 队列大小 */
		executor.setQueueCapacity(threadPool.getSendJob().getQueueCapacity());
		executor.setThreadNamePrefix(threadPool.getSendJob().getThreadNamePrefix());
		/** 线程最大空闲时间 */
		executor.setKeepAliveSeconds(threadPool.getSendJob().getKeepAliveSeconds());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}



}