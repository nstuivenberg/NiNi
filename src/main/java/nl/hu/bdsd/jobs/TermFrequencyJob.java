package nl.hu.bdsd.jobs;

import nl.hu.bdsd.domain.Message;
import nl.hu.bdsd.domain.MessageCounter;
import nl.hu.bdsd.domain.serde.MessageSerdeDeserializer;
import nl.hu.bdsd.domain.serde.MessageSerdeSerializer;
import nl.hu.bdsd.kafka_broker.IKafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This job counts the words and publishes them to the next topic
 */
public class TermFrequencyJob implements Runnable {
    private static String JOB_NAME = "TF-counter";
    private static String SOURCE_TOPIC = "nick.clean-data";
    private static String SINK_TOPIC = "nick.tf-data";

    private KafkaStreams streams;

    public TermFrequencyJob() {
        this.streams = this.buildKafkaStreams();
    }

    private KafkaStreams buildKafkaStreams() {
        final Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, JOB_NAME);

        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100 * 1000);

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Serde<Message> messageSerde = Serdes.serdeFrom(new MessageSerdeSerializer(),
                new MessageSerdeDeserializer());

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<Long, Message> readStream = builder.stream(SOURCE_TOPIC,
                Consumed.with(longSerde, messageSerde));

        /*
        Do not replace. We are not running java 9.
         */
        readStream.map(
                new KeyValueMapper<Long, Message, KeyValue<?, ?>>() {
                    @Override
                    public KeyValue<?, ?> apply(Long aLong, Message message) {
                        return new KeyValue<>(aLong, wordCounter(message));
                    }
                });

                readStream.to(SINK_TOPIC, Produced.with(Serdes.Long(),messageSerde));

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    private Message wordCounter(Message m) {

        final Map<String, MessageCounter> wordMap = new HashMap<>();
        String newsMessage = m.getMessageSource()
                .getSanitizedDescription().toLowerCase();

        for(String word : newsMessage.split("\\W")) {
            if(word.isEmpty()) {
                continue;
            }
            if(wordMap.containsKey(word)) {
                MessageCounter mc = wordMap.get(word);
                mc.addOneToInteger();
                wordMap.put(word, mc);
            } else {
                wordMap.put(word, new MessageCounter());
            }
        }
        return m;
    }

    @Override
    public void run() {
        doRun();
    }

    private void doRun() {
        streams.start();
    }
}
