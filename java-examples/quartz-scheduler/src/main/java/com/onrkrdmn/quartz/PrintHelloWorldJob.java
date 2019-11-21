package com.onrkrdmn.quartz;

import lombok.extern.java.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@QuartzJob
@Log
public class PrintHelloWorldJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info(this.getClass().getSimpleName() + " is running.....");
        System.out.println(">>>>>>>>>>>Hello Quartz Job<<<<<<<<<<<<<<<<<");
    }
}
