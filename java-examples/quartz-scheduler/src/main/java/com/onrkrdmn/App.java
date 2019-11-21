package com.onrkrdmn;


import com.onrkrdmn.quartz.QuartzJobStore;
import com.onrkrdmn.quartz.SchedulerService;
import com.onrkrdmn.quartz.SchedulerServiceImpl;
import org.quartz.Job;

import java.util.List;

public class App {

    // run every 10 seconds
    private static String CRON_STRING = "0/10 * * ? * * *";

    public static void main(String[] args) throws ClassNotFoundException {
        SchedulerService schedulerService = new SchedulerServiceImpl();
        QuartzJobStore quartzJobStore = QuartzJobStore.getInstance();
        quartzJobStore.printJobs();
        List<String> jobs = quartzJobStore.getJobs();

        // Get the first job
        String job1 = jobs.get(0);
        Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(job1);
        schedulerService.register(jobClass, CRON_STRING, true);
    }
}
