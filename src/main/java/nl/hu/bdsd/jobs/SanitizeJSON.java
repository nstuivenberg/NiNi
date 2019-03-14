package nl.hu.bdsd.jobs;

import nl.hu.bdsd.domain.Message;
import nl.hu.bdsd.domain.serde.MessageSerdeDeserializer;
import nl.hu.bdsd.domain.serde.MessageSerdeSerializer;
import nl.hu.bdsd.kafka_broker.IKafkaConstants;
import nl.hu.bdsd.util.Json2Object;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;

/**
 * Represents the first step after the spider has published the RAW json.
 * 1. Turn JSON to Objects
 * 2. Sanitize html.
 * 3. Remove 'leestekens'
 * 4. remove list of unwanted words like: de, het, een, van et cetera.
 */

public class SanitizeJSON implements Runnable {


    private static String SOURCE_TOPIC = "nick.raw";
    private static String SINK_TOPIC = "nick.clean-data";
    private KafkaStreams streams;

    private long counter = 0;

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

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<Long, String> readStream =
                builder.stream(SOURCE_TOPIC, Consumed.with(longSerde, stringSerde));

        /*
        DO not replace. We are not running java 9.
         */
        final KStream<Long, Message> converted = readStream.map(
                new KeyValueMapper<Long, String, KeyValue<? extends Long, ? extends Message>>() {
                    @Override
                    public KeyValue<? extends Long, ? extends Message> apply(Long aLong, String s) {
                        return new KeyValue<>(aLong, this.jsonToMessage(s));
                    }
                    private Message jsonToMessage(String json) {
                        Json2Object jo = new Json2Object();
                        return  jo.jsonToMesage(json);
                    }
                });
        converted.to(SINK_TOPIC, Produced.with(Serdes.Long(),
                        Serdes.serdeFrom(new MessageSerdeSerializer()
                                , new MessageSerdeDeserializer())));

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    @Override
    public void run() {
        doRun();
    }
    private void doRun(){
        streams.start();
    }
}
