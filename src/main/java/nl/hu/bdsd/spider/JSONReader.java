package nl.hu.bdsd.spider;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
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
                //JsonElement root = new JsonParser().parse(reader);
                //root.toString();
                System.out.println(reader.nextName());
                reader.skipValue();

                /*
                if (token.equals(JsonToken.NAME)) {
                    //get the current token
                    fieldname = reader.nextName();
                }

                if ("name".equals(fieldname)) {
                    //move to next token
                    token = reader.peek();
                    System.out.println("Name: "+reader.nextString());
                }

                if("age".equals(fieldname)) {
                    //move to next token
                    token = reader.peek();
                    System.out.println("Age:" + reader.nextInt());
                }

                if("verified".equals(fieldname)) {
                    //move to next token
                    token = reader.peek();
                    System.out.println("Verified:" + reader.nextBoolean());
                }
                */
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
