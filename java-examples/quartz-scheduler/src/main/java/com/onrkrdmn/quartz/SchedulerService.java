package com.onrkrdmn.quartz;

import org.quartz.Job;

public interface SchedulerService {

	void runOnce(Class<? extends Job> jobClass);

	void register(Class<? extends Job> jobClass, String cronExpression, boolean runImmediately);

	void reschedule(Class<? extends Job> jobClass, String cronExpression, boolean runImmediately);

	void delete(Class<? extends Job> jobClass);

	boolean isExisting(Class<? extends Job> jobClass);
}
