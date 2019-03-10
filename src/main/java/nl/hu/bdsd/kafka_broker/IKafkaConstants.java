package nl.hu.bdsd.kafka_broker;

public interface IKafkaConstants {

    String KAFKA_BROKERS = "nigelvanhattum.nl:9092";
    Integer MESSAGE_COUNT=1000;
    String CLIENT_ID="poliFLWProducer";
    //String TOPIC_NAME="demo";
    String GROUP_ID_CONFIG="PoliFLWConsumer";
    Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    String OFFSET_RESET_EARLIER="earliest";
    String OFFSET_RESET_LATEST="latest";
    Integer MAX_POLL_RECORDS=1;
}
