package nl.hu.bdsd.domain.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.bdsd.domain.Message;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MessageSerdeDeserializer implements Deserializer<Message> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Message deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Message message = null;
        try {
            message = mapper.readValue(bytes, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void close() {

    }
}
