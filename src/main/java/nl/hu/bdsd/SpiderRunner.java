package nl.hu.bdsd;

import nl.hu.bdsd.spider.JsonPReader;

public class SpiderRunner {

    public static void main(String[] args) {
        JsonPReader j = new JsonPReader();
        j.run();
    }
}
