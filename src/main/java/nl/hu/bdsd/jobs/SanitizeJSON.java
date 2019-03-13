package nl.hu.bdsd.jobs;

import nl.hu.bdsd.domain.Message;
import nl.hu.bdsd.kafka_broker.CusKafkaConsumer;
import nl.hu.bdsd.kafka_broker.IKafkaConstants;
import nl.hu.bdsd.util.Json2Object;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.zookeeper.KeeperException;

import java.time.Duration;
import java.util.Properties;

/**
 * Represents the first step after the spider has published the RAW json.
 * 1. Turn JSON to Objects
 * 2. Sanitize html.
 * 3. Remove 'leestekens'
 * 4. remove list of unwanted words like: de, het, een, van et cetera.
 */

public class SanitizeJSON implements Runnable {


    private static String SOURCE_TOPIC = "spider";
    private static String SINK_TOPIC = "unknown";
    private KafkaStreams streams;

    public SanitizeJSON() {
        this.streams = buildKafkaStream();
    }

    private KafkaStreams buildKafkaStream() {
        final Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "json-to-object-conversion");

        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100 * 1000);

        // Set up serializers and deserializers, which we will use for overriding the default serdes
        // specified above.
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        final StreamsBuilder builder = new StreamsBuilder();

        //final KStream<Long, String> inputTopic = builder.stream("spider");

        //String result = inputTopic.flatMapValues()

        final KStream<Long, String> aLittleStream =
                builder.stream(SOURCE_TOPIC, Consumed.with(longSerde, stringSerde));

        aLittleStream.foreach((k,v) -> forFun(v));

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    @Override
    public void run() {
        doRun();
    }

    private void doRun(){
        streams.start();
    }

    private void forFun(String message) {
        System.out.println("From le topic: " + message);
        Message m = jsonToMessage(message);
        System.out.println(m.toString());


    }

    private Message jsonToMessage(String json) {
        return Json2Object.jsonToMesage(json);

    }

    private void processJsonMessage() {

    }
}
