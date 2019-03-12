package nl.hu.bdsd.kafka_broker;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.zookeeper.KeeperException;
import scala.collection.immutable.Stream;

import java.util.Collections;
import java.util.Properties;

public class CusKafkaConsumer {
    private Consumer<Long, String> consumer;

    public Consumer<Long, String> getConsumer(String topicName) {
        if(consumer == null) {
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);
            consumer = new KafkaConsumer<Long, String>(props);
            consumer.subscribe(Collections.singletonList(topicName));
        }
        return consumer;
    }

    public Consumer<Long, String> getConsumer() throws KeeperException.NotEmptyException {
        if(consumer == null) {
            throw new KeeperException.NotEmptyException("Consumer not initialized. Try initializing it first.");
        }
        return consumer;
    }

    public CusKafkaConsumer(String topicName) {
        getConsumer(topicName);
    }
}
