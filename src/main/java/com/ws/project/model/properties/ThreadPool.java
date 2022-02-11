package com.ws.project.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPool {
	private ThreadPoolProperties core;
	private ThreadPoolProperties sendJob;
	private ThreadPoolProperties dbSave;
	private ThreadPoolProperties server;
}
