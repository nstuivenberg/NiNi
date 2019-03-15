package nl.hu.bdsd.textmining.tfidf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCount {

    public static HashMap<String, Double> getWordFrequence(String text) {
        HashMap<String, Integer> wordCount = getWordCount(text);


        HashMap<String ,Double> termFreqMap = new HashMap<>();
        double sum = 0.0;
        //Get the sum of all elements in hashmap
        for (float val : wordCount.values()) {
            sum += val;
        }

        //create a new hashMap with Tf values in it.
        Iterator it = wordCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            double tf = (Integer)pair.getValue()/ sum;
            termFreqMap.put((pair.getKey().toString()),tf);
        }
        return termFreqMap;
    }

    public static HashMap<String, Integer> getWordCount(String text) {
        HashMap<String,Integer> WordCount = new HashMap<String,Integer>();
        HashMap<String, Integer> finalMap;
        String[] words = text.toLowerCase().split(" ");
            for(String term : words)
            {
                //cleaning up the term ie removing .,:"
                term = cleanseInput(term);
                //ignoring numbers
                if(isDigit(term))
                {
                    continue;
                }
                if(term.length() == 0)
                {
                    continue;
                }
                if(WordCount.containsKey(term))
                {
                    WordCount.put(term,WordCount.get(term)+1);
                }
                else
                {
                    WordCount.put(term,1);
                }
            }
        // sorting the hashmap
        Map<String, Integer> treeMap = new TreeMap<>(WordCount);
        finalMap = new HashMap<String, Integer>(treeMap);
        return finalMap;
    }

    //cleaning up the input by removing .,:"
    private static String cleanseInput(String input) {
        String newStr = input.replaceAll("[, . : ;\"]", "");
        newStr = newStr.replaceAll("\\p{P}","");
        newStr = newStr.replaceAll("\t","");
        return newStr;
    }

    //Returns if input contains numbers or not
    private static  boolean isDigit(String input) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        }
        return false;
    }
}
