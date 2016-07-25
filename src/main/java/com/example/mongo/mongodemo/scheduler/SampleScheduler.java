package com.example.mongo.mongodemo.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SampleScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(SampleScheduler.class);
	
	@Autowired
	private ThreadPoolTaskScheduler scheduler;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@PostConstruct
	void start() {
		//schedule();
		scheduleThreadPool();
	}

	
	public void scheduleThreadPool() {
		LOG.debug("Starting -----------------------> ");
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				LOG.debug("Started Running Scheduler task --->");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				LOG.debug("Completed task");
			}
		}, 5000);
	}
	
	public void schedule() {
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOG.debug("Started Running Scheduler task --->");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				LOG.debug("Completed task");
			}
		}, 1000);
	}

	@PreDestroy
	void destroy() {
		LOG.info("Predestroy of scheduler --->{} :: {}", taskExecutor.getActiveCount(), scheduler.getActiveCount());
		scheduler.shutdown();
		taskExecutor.shutdown();
	}

}
