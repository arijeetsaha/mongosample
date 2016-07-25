package com.example.mongo.mongodemo.handler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(ContextClosedHandler.class);

	@Autowired
	ThreadPoolTaskScheduler scheduler;

	@Override
	public void onApplicationEvent(ContextClosedEvent context) {
		LOG.debug("ContextClosed event received ------------->");
		scheduler.shutdown();

		Map<String, ThreadPoolTaskScheduler> schedulers = context.getApplicationContext().getBeansOfType(ThreadPoolTaskScheduler.class);
		
		for (ThreadPoolTaskScheduler scheduler : schedulers.values()) {
			scheduler.getScheduledExecutor().shutdown();
			try {
				scheduler.getScheduledExecutor().awaitTermination(20 * 1000, TimeUnit.MILLISECONDS);
				if (scheduler.getScheduledExecutor().isTerminated() || scheduler.getScheduledExecutor().isShutdown()) {
					LOG.debug("Scheduler {} is terminated", scheduler.getThreadNamePrefix());
				} else {
					LOG.info("Scheduler " + scheduler.getThreadNamePrefix()
							+ " has not stoped normally and will be shut down immediately");
					scheduler.getScheduledExecutor().shutdownNow();
					LOG.info("Scheduler " + scheduler.getThreadNamePrefix() + " has shut down immediately");

				}
			} catch (IllegalStateException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		Map<String, ThreadPoolTaskExecutor> executers = context.getApplicationContext().getBeansOfType(ThreadPoolTaskExecutor.class);

		for (ThreadPoolTaskExecutor executor : executers.values()) {
			int retryCount = 0;
			while (executor.getActiveCount() > 0 && ++retryCount < 51) {
				try {
					LOG.info("Executer " + executor.getThreadNamePrefix() + " is still working with active "
							+ executor.getActiveCount() + " work. Retry count is " + retryCount);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!(retryCount < 51))
				LOG.info("Executer " + executor.getThreadNamePrefix()
						+ " is still working.Since Retry count exceeded max value " + retryCount
						+ ", will be killed immediately");
			executor.shutdown();
			LOG.info("Executer " + executor.getThreadNamePrefix() + " with active " + executor.getActiveCount()
					+ " work has killed");
		}
	}

}
