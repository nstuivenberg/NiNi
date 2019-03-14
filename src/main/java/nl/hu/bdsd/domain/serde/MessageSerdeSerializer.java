package nl.hu.bdsd.domain.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.bdsd.domain.Message;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Map;

public class MessageSerdeSerializer implements Serializer<Message> {


    //private final Serializer<Message> messageSerializer;


    //public MessageSerdeSerializer(Serializer<Message> messageSerializer) {
    //    this.messageSerializer = messageSerializer;
    //}

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Message message) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(message).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
