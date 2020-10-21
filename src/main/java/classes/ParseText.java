package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ParseText {

    private static final String REG_EXP = "[\\s-,.!?\";:«»\\[\\]()\n\r\t]+";

    public static HashMap parseHtmlFromFile(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");

        String[] arrayOfWords = doc.text().toLowerCase().split(REG_EXP);

        HashMap<String, Integer> map = new HashMap<>();
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
}
