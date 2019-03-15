package nl.hu.bdsd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRunner {

    public static void main(String[] args) {
        TopicTester t = new TopicTester();

        ExecutorService exec = Executors.newFixedThreadPool(1);
        t.run();

    }
}
