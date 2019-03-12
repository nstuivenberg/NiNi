package nl.hu.bdsd.kafka_broker;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CusKafkaProducer {
    private KafkaProducer<Long, String > producer;

    private KafkaProducer<Long, String > getProducer() {
        if(producer == null) {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
            producer = new KafkaProducer<Long, String>(props);
        }
        return  producer;
    }

    public CusKafkaProducer(){
        getProducer();
    }

    public void produce(String topicName, String value) throws ExecutionException, InterruptedException {
            ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(topicName, value);
            // send the messege and check if it got sent.
            producer.send(record).get();
    }
}
