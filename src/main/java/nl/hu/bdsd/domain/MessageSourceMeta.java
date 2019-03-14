package nl.hu.bdsd.domain;

import java.util.HashMap;

public class MessageSourceMeta {
    private String processingStarted;
    private String processingFinished;
    private String rights;
    private String collection;

    private String originalObjectId;
    private String sourceId;
    private String id; //dubbelop, zit ook in MessageSource
    private MessageSourceMetaOOU originalObjectUrls;

    private String pfl_url;

    public HashMap<String, Integer> getWordCount() {
        return wordCount;
    }

    public void setWordCount(HashMap<String, Integer> wordCount) {
        this.wordCount = wordCount;
    }

    private HashMap<String, Integer> wordCount;

    public String getProcessingStarted() {
        return processingStarted;
    }

    public void setProcessingStarted(String processingStarted) {
        this.processingStarted = processingStarted;
    }

    public String getProcessingFinished() {
        return processingFinished;
    }

    public void setProcessingFinished(String processingFinished) {
        this.processingFinished = processingFinished;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getOriginalObjectId() {
        return originalObjectId;
    }

    public void setOriginalObjectId(String originalObjectId) {
        this.originalObjectId = originalObjectId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageSourceMetaOOU getOriginalObjectUrls() {
        return originalObjectUrls;
    }

    public void setOriginalObjectUrls(MessageSourceMetaOOU originalObjectUrls) {
        this.originalObjectUrls = originalObjectUrls;
    }

    public String getPfl_url() {
        return pfl_url;
    }

    public void setPfl_url(String pfl_url) {
        this.pfl_url = pfl_url;
    }
}
