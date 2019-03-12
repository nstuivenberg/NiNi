package nl.hu.bdsd.kafka_broker;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.ExecutionException;

public class KafkaConnectionTest {
    static String DEMOTOPICNAME = "demo";
    public static void main(String[] args) throws InterruptedException, KeeperException.NotEmptyException, ExecutionException {

        runProducer();

        Thread.sleep(3000);
        System.out.println("Starting...");

        runConsumer();
    }
    static void runConsumer() throws KeeperException.NotEmptyException {
        CusKafkaConsumer consumer = new CusKafkaConsumer(DEMOTOPICNAME);
        int noMessageFound = 0;
        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.getConsumer().poll(1000);
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }
            //print each record.
            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
            });
            // commits the offset of record to broker.
            consumer.getConsumer().commitAsync();
        }
        consumer.getConsumer().close();
    }
    static void runProducer() throws ExecutionException, InterruptedException {
        CusKafkaProducer producer = new CusKafkaProducer();
        for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
            producer.produce(DEMOTOPICNAME,"This is record " + index);
        }
    }
}
