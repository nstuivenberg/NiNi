package nl.hu.bdsd;

import nl.hu.bdsd.jobs.CorpusJob;
import nl.hu.bdsd.jobs.SanitizeJSON;
import nl.hu.bdsd.jobs.TermFrequencyJob;
import nl.hu.bdsd.spider.JsonPReader;

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

        jobs.add(new JsonPReader());
        jobs.add(new SanitizeJSON());
        jobs.add(new TermFrequencyJob());
        jobs.add(new CorpusJob());

        ExecutorService exec = Executors.newFixedThreadPool(1);

        for (Runnable r : jobs) {
            exec.submit(r);
        }

        exec.shutdown();
    }
}
