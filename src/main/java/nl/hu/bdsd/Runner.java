package nl.hu.bdsd;

import nl.hu.bdsd.jobs.SanitizeJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Starts all jobs
 */

public class Runner {
    public static void main(String[] args) {

        List<Runnable> jobs = new ArrayList<>();

        SanitizeJSON s = new SanitizeJSON();
        //jobs.add(s);
        //jobs.add(new JsonPReader());
        jobs.add(new TopicTester());

        ExecutorService exec = Executors.newFixedThreadPool(1);

        for (Runnable r : jobs) {
            exec.submit(r);
        }

        exec.shutdown();
    }
}
