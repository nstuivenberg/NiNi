package nl.hu.bdsd.jobs;

import nl.hu.bdsd.domain.Message;
import nl.hu.bdsd.domain.serde.MessageSerdeDeserializer;
import nl.hu.bdsd.domain.serde.MessageSerdeSerializer;
import nl.hu.bdsd.kafka_broker.IKafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Kafka-streams job that counts the amount of terms per message
 * and adds them to the message object.
 */

public class CorpusJob implements Runnable {
    private static String JOB_NAME = "wordcounter";
    private static String SOURCE_TOPIC = "nick.clean-data";
    private static String SINK_TOPIC = "nick.corpus";

    private KafkaStreams streams;

    public CorpusJob() {
        this.streams = this.buildKafkaStreams();
    }

    private KafkaStreams buildKafkaStreams() {
        final Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, JOB_NAME);

        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100 * 1000);

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Serde<Message> messageSerde = Serdes.serdeFrom(new MessageSerdeSerializer(),
                new MessageSerdeDeserializer());

        final StreamsBuilder builder = new StreamsBuilder();

        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        final KStream<Long, Message> readStream = builder.stream(SOURCE_TOPIC,
                Consumed.with(Serdes.Long(), Serdes.serdeFrom(new MessageSerdeSerializer(),
                        new MessageSerdeDeserializer())));

        //readStream.foreach((k, v) -> {System.out.println(v);});

        final KTable<String, Long> wordCounts = readStream
                .flatMapValues(value -> Arrays
                        .asList(pattern.split(value.getMessageSource().getSanitizedDescription().toLowerCase())))
                .groupBy((key, word) -> word)
                .count();

        // Something something publish
        wordCounts.toStream().to(SINK_TOPIC, Produced.with(stringSerde, longSerde));

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    @Override
    public void run() {
        doRun();
    }

    private void doRun() {
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
