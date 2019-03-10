package nl.hu.bdsd;

import nl.hu.bdsd.spider.JsonPReader;

/**
 * This simulates a spider that crawls the web.
 *
 * The spider program represents the spider that crawls the web looking for news-articles.
 * This spides reads it files from a JSON-file and publishes them one by one onto the messagebus.
 *
 * @since   2019-03-08
 */

public class Spider {
    public static void main(String[] args) {
        JsonPReader j = new JsonPReader();
    }
}
