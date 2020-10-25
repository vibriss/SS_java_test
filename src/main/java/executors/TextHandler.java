package executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class is used for parsing text from html code, splitting it for words and counting this words.
 */

public class TextHandler {

    /**
     * Regular expression for splitting text.
     * Used dividers:
     * {' ', ',', '.', '!', '?','"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'}
     */
    private static final String REG_EXP = "[\\s-,.!?\";:«»\\[\\]()\n\r\t]+";

    /**
     * Method parses text from html code got from file using Jsoup library.
     * @param file         input file, containing html code to parse
     * @return map         HashMap filled with unique words and their counts
     * @throws IOException if smth goes wrong
     */
    public Map parseHtmlFromFile(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");

        String[] arrayOfWords = doc.text().toLowerCase().split(REG_EXP);

        Map<String, Integer> map = new HashMap<>();
        for (String word : arrayOfWords) {
            if (map.containsKey(word)) {
                int count = map.get(word);
                map.replace(word, count, ++count);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }

    /**
     * Method prints out the HashMap that contains unique words with their counts.
     * @param map Hashmap<String, Integer> where String is a unique word and Integer is a count of this word
     */
    public void print(Map<String, Integer> map) {
        for (Map.Entry pair : map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }
}
