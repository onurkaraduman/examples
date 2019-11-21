package com.onrkrdmn.quartz;

import lombok.extern.java.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.logging.Level;

@Log
public class SchedulerServiceImpl implements SchedulerService {

    private Scheduler scheduler;

    public SchedulerServiceImpl() {
        init();
    }

    private void init() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            log.log(Level.SEVERE, "couldn't get scheduler. None of schedule will work", e);
        }
    }

    @Override
    public void runOnce(Class<? extends Job> jobClass) {
        try {
            String name = jobClass.getSimpleName();
            JobKey jobKey = JobKey.jobKey(name);
            if (!scheduler.checkExists(jobKey)) {
                JobDetail jobDetail = JobBuilder.newJob(jobClass)
                        .withIdentity(name)
                        .storeDurably(true)
                        .build();
                scheduler.addJob(jobDetail, true);

            }
            scheduler.triggerJob(jobKey);

        } catch (SchedulerException e) {
            log.log(Level.SEVERE, "Couldn't run QuartzJob:" + jobClass, e);
        }

    }

    @Override
    public void register(Class<? extends Job> jobClass, String cronExpression, boolean runImmediately) {
        try {
            String name = jobClass.getSimpleName();
            JobKey jobKey = JobKey.jobKey(name);
            if (scheduler.checkExists(jobKey)) {
                reschedule(jobClass, cronExpression, runImmediately);
            } else {
                registerJob(jobClass, cronExpression, name);
            }
            if (runImmediately) {
                scheduler.triggerJob(jobKey);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Couldn't be scheduled. QuartzJob:" + jobClass, e);
        }
    }

    @Override
    public void reschedule(Class<? extends Job> jobClass, String cronExpression, boolean runImmediately) {
        try {
            String name = jobClass.getSimpleName();
            JobKey jobKey = JobKey.jobKey(name);
            if (scheduler.checkExists(jobKey)) {
                rescheduleJob(cronExpression, name);
            } else {
                registerJob(jobClass, cronExpression, name);
            }
            if (runImmediately) {
                scheduler.triggerJob(jobKey);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Couldn't be rescheduled. QuartzJob:" + jobClass, e);
        }
    }

    @Override
    public void delete(Class<? extends Job> jobClass) {
        try {
            if (isExisting(jobClass)) {
                scheduler.deleteJob(JobKey.jobKey(jobClass.getSimpleName()));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Couldn't be rescheduled. QuartzJob:" + jobClass, e);
        }
    }

    @Override
    public boolean isExisting(Class<? extends Job> jobClass) {
        try {

            String name = jobClass.getSimpleName();
            return scheduler.checkExists(JobKey.jobKey(name));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Couldn't check if job isExisting. QuartzJob:" + jobClass, e);
            return false;
        }
    }

    private void rescheduleJob(String cronExpression, String name) throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(name).withSchedule(cronScheduleBuilder).build();
        scheduler.rescheduleJob(TriggerKey.triggerKey(name), cronTrigger);
    }

    private void registerJob(Class<? extends Job> jobClass, String cronExpression, String name) throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name).build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(name).withSchedule(cronScheduleBuilder).build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
