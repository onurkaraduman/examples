package com.onrkrdmn.quartz;

import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This is charge of reading all classes which has {@link QuartzJob} annotation and storing in the map
 */
@Log
public class QuartzJobStore {

    private List<String> jobs = new ArrayList<>();


    private QuartzJobStore() {
        init();
    }

    private void init() {
        Reflections reflections = new Reflections("com.onrkrdmn.quartz");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(QuartzJob.class);
        StringBuilder sb = new StringBuilder("Available Quartz QuartzJob\n");
        annotated.stream().forEach(s -> sb.append(s.getSimpleName()).append("\n"));
        log.info(sb.toString());
        annotated.stream().forEach(s -> {
            jobs.add(s.getTypeName());
        });
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void printJobs() {
        log.info("AvailableJobs:" + String.join(",", jobs));
    }


    public static QuartzJobStore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static QuartzJobStore INSTANCE = new QuartzJobStore();
    }
}
