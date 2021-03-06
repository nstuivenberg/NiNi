package nl.hu.bdsd.util;

import nl.hu.bdsd.domain.Message;
import nl.hu.bdsd.domain.MessageSource;
import nl.hu.bdsd.domain.MessageSourceMeta;
import nl.hu.bdsd.domain.MessageSourceMetaOOU;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Bevat statische methodes die een JSON bericht omzetten naar een Message-klasse (inclusief relaties).
 */

/*
 * TODO: Deze lijst afmaken
 * TODO: Arrays verwerken
 */
public class Json2Object {

    private final static Logger LOGGER = Logger.getLogger(Json2Object.class.getName());

    private Message m = new Message();
    private MessageSource messageSource = new MessageSource();
    private MessageSourceMeta messageSourceMeta = new MessageSourceMeta();
    private MessageSourceMetaOOU messageSourceMetaOOU = new MessageSourceMetaOOU();

    public Message jsonToMesage(String json) {

        this.readJson(json);
        return m;
    }

    private void readJson(String json) {

        InputStream input = new ByteArrayInputStream(json.getBytes());
        JsonParser jsonParser = Json.createParser(input);


        String keyName = null;
        while(jsonParser.hasNext()) {
            JsonParser.Event event = jsonParser.next();
            switch (event) {
                case KEY_NAME:
                    keyName = jsonParser.getString();
                    break;
                case VALUE_STRING:
                    setStringValues(m, messageSource, messageSourceMeta, messageSourceMetaOOU, keyName,
                            jsonParser.getString());
                    break;
                case VALUE_NUMBER:
                    setIntValues(messageSource, keyName, jsonParser.getInt());
                    break;
                //case VALUE_FALSE:
                //    setBoolValues(messageSource, keyName, false);
                //    break;
                //case VALUE_TRUE:
                //    setBoolValues(messageSource, keyName, true);
                //    break;
            }
        }

        messageSourceMeta.setOriginalObjectUrls(messageSourceMetaOOU);
        messageSource.setMeta(messageSourceMeta);
        m.setMessageSource(messageSource);
    }

    private void setStringValues(Message message, MessageSource mSource, MessageSourceMeta msMeta,
                                        MessageSourceMetaOOU msmOOU, String keyName, String value) {
        if (keyName.equals("_type")) {
            message.setType(value);
        } else if (keyName.equals("_source")) {
            if (value != null)
                message.setScore("value");
        } else if (keyName.equals("_index")) {
            message.setIndex(value);
        } else if (keyName.equals("_id")) {
            message.setId(value);
            msMeta.setId(value);
        } else if (keyName.equals("description")) {
            mSource.setDescription(value);
            mSource.setSanitizedDescription(value);
        } else if (keyName.equals("source")) {
            mSource.setSource(value);
        } else if (keyName.equals("location")) {
            mSource.setLocation(value);
        } else if (keyName.equals("all_text")) {
            mSource.setAll_text(value);
        } else if (keyName.equals("date")) {
            mSource.setDate(value);
        } else if (keyName.equals("type")) {
            mSource.setType(value);
        } else if (keyName.equals("title")) {
            mSource.setTitle(value);
        } else if (keyName.equals("processing_started")) {
            msMeta.setProcessingStarted(value);
        } else if (keyName.equals("processing_finished")) {
            msMeta.setProcessingFinished(value);
        } else if (keyName.equals("rights")) {
            msMeta.setRights(value);
        } else if (keyName.equals("collection")) {
            msMeta.setCollection(value);
        } else if (keyName.equals("original_object_id")) {
            msMeta.setOriginalObjectId(value);
        } else if (keyName.equals("source_id")) {
            msMeta.setSourceId(value);
        } else if (keyName.equals("pfl_url")) {
            msMeta.setPfl_url(value);
        } else if(keyName.equals("html")) {
            msmOOU.setHtml(value);
        } else {
            LOGGER.warning("Unknown key: " + keyName);
        }
    }

    private void setIntValues(MessageSource mSource, String keyName, int value) {
        if(keyName.equals("date_granularity")) {
            mSource.setDate_granulariry(value);
        }
    }

    /*
     * Broken
     */
    private void setBoolValues(MessageSource messageSource, String keyName, boolean value) {
        if(keyName.equals("hidden")) {
            messageSource.setHidden(value);
        }
    }
}
