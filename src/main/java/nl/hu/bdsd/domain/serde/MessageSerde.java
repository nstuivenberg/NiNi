package nl.hu.bdsd.domain.serde;

import nl.hu.bdsd.domain.Message;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class MessageSerde implements Serde<Message> {

    private final Serde<Message> inner;

    public MessageSerde(Serde<Message> inner) {
        this.inner = Serdes.serdeFrom(new MessageSerdeSerializer(),
                new MessageSerdeDeserializer());
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<Message> serializer() {
        return null;
    }

    @Override
    public Deserializer<Message> deserializer() {
        return null;
    }
}
