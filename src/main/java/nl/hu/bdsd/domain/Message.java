package nl.hu.bdsd.domain;

/**
 * Holds the result of the spider and the various micro-jobs
 *
 */

public class Message {

    //Dit is een array in het JSON-bestand, maar dat is en beetje nutteloos.
    private int sort;
    private String type; //Enum?
    private MessageSource messageSource;
    private String score;
    private String index;
    private String id;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n id: " + this.getId());
        sb.append("\n index: " + this.getIndex());
        if(this.getScore() != null) {
            sb.append("\n score: " + this.getScore());
        }

        return sb.toString();
    }
}
