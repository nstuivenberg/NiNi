package nl.hu.bdsd.spider;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 *
 * @since   2019-03-08
 */
public class JSONReader {

    private final static Logger LOGGER = Logger.getLogger(JSONReader.class.getName());
    JSONParser parser = new JSONParser();
    JSONArray a;

    public JSONReader() {
        try {
            a = (JSONArray) parser.parse(new FileReader("F:\\IdeaProjects\\poliflow\\src\\main\\resources\\complete-dump.json"));
        } catch(FileNotFoundException f) {
            f.printStackTrace();
            LOGGER.severe("Could not find file");
        } catch(IOException io) {
            io.printStackTrace();
        } catch(ParseException p) {
            p.printStackTrace();
        }
        this.doSth();
    }

    private void doSth() {
        System.out.println("Er zijn " + a.size() + " objecten in de array");

    }


}
