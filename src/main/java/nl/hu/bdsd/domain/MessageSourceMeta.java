package nl.hu.bdsd.domain;

import java.time.Instant;

public class MessageSourceMeta {
    private Instant processingStarted;
    private Instant processingFinished;
    private String rights;
    private String collection;

    private String originalObjectId;
    private String sourceId;
    private String id; //dubbelop, zit ook in MessageSource
    private MessageSourceMetaOOU originalObjectUrls;

    public Instant getProcessingStarted() {
        return processingStarted;
    }

    public void setProcessingStarted(Instant processingStarted) {
        this.processingStarted = processingStarted;
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
}
