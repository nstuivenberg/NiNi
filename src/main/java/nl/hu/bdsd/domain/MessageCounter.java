package nl.hu.bdsd.domain;

public class MessageCounter {

    private Double tfidf;
    private Integer wordCountedInText;

    public MessageCounter() {
        tfidf = 0.0; //0D
        wordCountedInText = 1;
    }

    public void addOneToInteger() {
        this.wordCountedInText++;
    }
    public void calculatetfIdf(int amountOfWordsCountedInCorpus) {
        this.tfidf = Math.log((amountOfWordsCountedInCorpus * 1.0) / this.wordCountedInText);
    }
}
