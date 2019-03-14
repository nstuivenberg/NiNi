package nl.hu.bdsd.domain;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MessageSource {

    private final static Logger LOGGER = Logger.getLogger(MessageSource.class.getName());
    private String description;

    private String sanitizedDescription;

    private int date_granulariry;
    private String source;

    private MessageSourceMeta meta;
    private String location;
    
    private List<String> parties;

    //Niet nodig om met date objecten te gaan kloten
    private String date;
    private String all_text;
    private String type;

    private boolean hidden;

    private String title;

    private final static String FILELOCATION = "src/main/resources/stopwordsNL.txt";

    //TODO Enrichments, wat zijn deze?

    public MessageSource() {
        parties = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDate_granulariry() {
        return date_granulariry;
    }

    public void setDate_granulariry(int date_granulariry) {
        this.date_granulariry = date_granulariry;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public MessageSourceMeta getMeta() {
        return meta;
    }

    public void setMeta(MessageSourceMeta meta) {
        this.meta = meta;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getParties() {
        return parties;
    }

    public void setParties(List<String> parties) {
        this.parties = parties;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAll_text() {
        return all_text;
    }

    public void setAll_text(String all_text) {
        this.all_text = all_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSanitizedDescription(String sanitizedDescription) {

        sanitizedDescription = sanitizedDescription.replaceAll(">","> ");
        String finalStr =
                Arrays.stream(sanitizedDescription.split("\\s+")) // Splitting you string
                        .map(str -> transformWord(str)) // Map as per your logic
                        .collect(Collectors.joining(" ")); // Joining back with space
        String safe = Jsoup.clean(finalStr, Whitelist.none());

        this.sanitizedDescription = safe;
        //System.out.println("\n------------------------------\n");
        //System.out.println(this.sanitizedDescription);
    }
    private String transformWord(String s) {
        //String[] bannedWords = {"de", "het", "en", "een"};
    try {
        List<String> lines = Files.readAllLines(Paths.get(FILELOCATION), Charset.defaultCharset());
        String[] bannedWords = lines.toArray(new String[lines.size()]);
        for (String b : bannedWords) {
            if (s.toLowerCase().equals(b.toLowerCase())) {
                return "";
            }
        }
    } catch(IOException io) {
        LOGGER.severe("Could not find list of banned words");
    }
        return s;
    }
}
