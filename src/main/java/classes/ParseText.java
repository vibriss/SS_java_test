package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseText {
    private Map<String, Integer> map;

    private static final String REG_EXP = "[\\s-,.!?\";:«»\\[\\]()\n\r\t]+";

    public Map parseHtmlFromFile(File file) throws IOException {
        Document doc = Jsoup.parse(file, "UTF-8");

        String[] arrayOfWords = doc.text().toLowerCase().split(REG_EXP);

        this.map = new HashMap<>();
        for (String word : arrayOfWords) {
            if (this.map.containsKey(word)) {
                int count = map.get(word);
                this.map.replace(word, count, ++count);
            } else {
                this.map.put(word, 1);
            }
        }
        return this.map;
    }
}
