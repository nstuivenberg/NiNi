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

}
