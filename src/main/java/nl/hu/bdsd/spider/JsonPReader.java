package nl.hu.bdsd.spider;

import nl.hu.bdsd.kafka_broker.CusKafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class JsonPReader {

    private final static Logger LOGGER = Logger.getLogger(JsonPReader.class.getName());
    //private final static String FILELOCATION = "src/main/resources/complete-dump.json";
    private final static String FILELOCATION = "C:/Users/nigel/Dropbox/School/hu/Jaar 5/Big Data System Design/complete-dump.json";
    private InputStream targetStream = null;

    public JsonPReader() {
        CusKafkaProducer producer = new CusKafkaProducer();
        try {
            File file = new File(FILELOCATION);
            targetStream = new FileInputStream(file);
        } catch (FileNotFoundException f) {
            LOGGER.severe("File not found!");
        }
        JsonParser parser = Json.createParser(targetStream);

        while (parser.hasNext()) {
            final JsonParser.Event event = parser.next();
            switch(event) {
                case START_ARRAY:
                    Stream<JsonValue> o = parser.getArrayStream();
                    o.forEach(s -> {
                        try {
                            producer.produce("spider",s.toString());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    break;
            }
        }
        parser.close();
        try {
            targetStream.close();
        } catch (IOException io) {
            LOGGER.severe("IO exceptie.");
        }

    }

    private void publishMessagToKafka(String jsonMessage) throws ExecutionException, InterruptedException {
        System.out.println(jsonMessage);
        CusKafkaProducer producer = new CusKafkaProducer();

        producer.produce("spider", jsonMessage);
        Thread.sleep(1000);
    }
}
