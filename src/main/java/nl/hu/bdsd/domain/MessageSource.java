package nl.hu.bdsd.domain;

import org.owasp.html.ElementPolicy;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.ArrayList;
import java.util.List;

//TODO title

public class MessageSource {
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

    //TODO Enrichments, wat zijn deze?
    //TODO Hidden

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

    public void setSanitizedDescription(String sanitizedDescription) {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .toFactory();
        String txt =  policy.sanitize(sanitizedDescription).replaceAll("\\<.*?\\>", "");
        this.sanitizedDescription = txt;
        System.out.println("\n------------------------------\n");
        System.out.println(this.sanitizedDescription);
    }
}
