package nl.hu.bdsd;

import nl.hu.bdsd.jobs.SanitizeJSON;
import nl.hu.bdsd.spider.JsonPReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Starts all jobs
 */

public class Runner {
    public static void main(String[] args) {

        List<Runnable> jobs = new ArrayList<>();

        SanitizeJSON s = new SanitizeJSON();
        jobs.add(s);
        jobs.add(new JsonPReader());
        jobs.add(new TopicTester());

        for (Runnable r : jobs) {
            r.run();
        }
    }
}
