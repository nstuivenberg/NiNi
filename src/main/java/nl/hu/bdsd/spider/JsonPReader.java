package nl.hu.bdsd.spider;

import nl.hu.bdsd.domain.Message;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class JsonPReader {

    private final static Logger LOGGER = Logger.getLogger(JsonPReader.class.getName());
    private final static String FILELOCATION = "src/main/resources/complete-dump.json";
    private InputStream targetStream = null;

    private List<Message> messageList;

    public JsonPReader() {
        try {
            File file = new File(FILELOCATION);
            targetStream = new FileInputStream(file);
        } catch (FileNotFoundException f) {
            LOGGER.severe("File not found!");
        }

        JsonParser parser = Json.createParser(targetStream);

        String key = null;
        String value = null;

        while (parser.hasNext()) {
            final JsonParser.Event event = parser.next();
            switch(event) {
                case KEY_NAME:
                    key = parser.getString();
                    System.out.println(key);
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    System.out.println(value);
                    break;
            }
        }
        parser.close();
    }
}
