package nl.hu.bdsd.spider;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import nl.hu.bdsd.domain.Message;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 *
 * @since   2019-03-08
 */
public class JSONReader {

    private final static Logger LOGGER = Logger.getLogger(JSONReader.class.getName());
    private final static String FILELOCATION = "src/main/resources/complete-dump.json";
    private InputStream targetStream = null;

    private List<Message> messageList;

    public JSONReader() {

        try {
            File file = new File(FILELOCATION);
            targetStream = new FileInputStream(file);
        } catch (FileNotFoundException f) {
            LOGGER.severe("File not found!");
        }

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(targetStream, "UTF-8"));

            handleJsonArray(reader);

            reader.close();
        } catch(UnsupportedEncodingException u) {
            u.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static void handleJsonObject(JsonReader reader) throws IOException{
        reader.beginObject();
        String fieldname = null;



        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.BEGIN_ARRAY)) {
                System.out.print("Marks [ ");
                handleJsonArray(reader);
                System.out.print("]");
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                return;
            } else {
                //Only doing Message-Object now
                if (token.equals(JsonToken.NAME)) {
                    //get the current token
                    fieldname = reader.nextName();
                }

                if ("_type".equals(fieldname)) {
                    //Alle types hebben als waarde item
                    token = reader.peek();
                    System.out.println("_type: " + reader.nextString());
                } else if("_score".equals(fieldname)) {
                    //TODO: hier doorgaan
                } else {
                    reader.skipValue();
                }


            }
        }

    }

    private static void handleJsonArray(JsonReader reader) throws IOException{
        reader.beginArray();
        String fieldname = null;

        while (true) {
            JsonToken token = reader.peek();

            if(token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleJsonObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else {
                System.out.println(reader.nextInt() + " ");
            }

        }

    }

}
