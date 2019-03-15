package nl.hu.bdsd;

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

import java.util.Properties;

public class TopicTester implements Runnable {

    private static String SOURCE_TOPIC = "nick.corpus";
    private static String SINK_TOPIC = "clean_data";
    private KafkaStreams streams;

    public TopicTester() {
        this.streams = buildKafkaStream();
    }

    private KafkaStreams buildKafkaStream() {
        final Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "topic-tester");

        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100 * 1000);

        final Serde<Long> longSerde = Serdes.Long();
        final Serde<Message> messageSerde = Serdes.serdeFrom(new MessageSerdeSerializer(),
                new MessageSerdeDeserializer());

        final StreamsBuilder builder = new StreamsBuilder();

        //final KStream<Long, Message> readStream = builder.stream(SOURCE_TOPIC,
        //        Consumed.with(longSerde, messageSerde));
        final KStream<String, Long> readStream = builder.stream(SOURCE_TOPIC,
                Consumed.with(Serdes.String(), Serdes.Long()));

        readStream.foreach(
        //        (k, m) -> printMessageObject(m));
                (k, m) -> printMessageObject(k + " aantal: " + m));

        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    //private void printMessageObject(Message m) {
    private void printMessageObject(String s) {
        //System.out.println("Topic Tester: " + m.toString());
        System.out.println(s);
        System.out.println("-------------------------------");
    }

    @Override
    public void run() {
        doRun();
    }

    private void doRun() {
        streams.start();
    }
}
