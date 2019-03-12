package nl.hu.bdsd.spider;

import nl.hu.bdsd.kafka_broker.ProducerCreator;
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
    private final static String FILELOCATION = "src/main/resources/complete-dump.json";
    private InputStream targetStream = null;

    public JsonPReader() {
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
                    o.forEach(s -> this.publishMessagToKafka(s.toString()));
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

    private void publishMessagToKafka(String jsonMessage) {
        System.out.println(jsonMessage);
        Producer<Long, String> producer = ProducerCreator.createProducer();

        ProducerRecord<Long, String> record = new ProducerRecord<>("spider", jsonMessage);

        try {
            RecordMetadata metadata = producer.send(record).get();
        } catch (ExecutionException e) {
            System.out.println("Error in sending record");
        } catch (InterruptedException i) {
            System.out.println("Error in sending record");
        }

        try {
            Thread.sleep(1000);
        } catch(InterruptedException i) {
            i.printStackTrace();
        }
    }
}
