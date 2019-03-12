package nl.hu.bdsd.domain;

import java.util.List;

public class MessageSource {
    private String description;
    private int date_granulariry;
    private String source;

    private MessageSourceMeta meta;
    private String location;
    
    private List<String> parties;

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
}
