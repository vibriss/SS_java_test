import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class is used for counting unique words on the webpage.
 */
public class WebPageParser {

    private static final String FILE_DIR = "files/";
    private static final String FILE_EXTENSION = ".html";
    private static final String REG_EXP = "[\\s-,.—!?\";:«»\\[\\]()\n\r\t]+";
    private static Logger log = Logger.getLogger(WebPageParser.class.getName());

    public void execute() {
        try {
            LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream("/logging.properties"));

            System.out.println("Enter page url as \"https://abc.com/\"");

            String urlString = readStringFromConsole();
            log.info("entered url string \"" + urlString + "\"");

            File file = saveWebPageToFile(urlString);
            log.info("web page saved to file: " + file.getName());

            Document document = parseHtmlFromFile(file);
            log.info("html file parsed to document");

            String[] arrayOfWords = splitDocumentByRegExp(document, REG_EXP);
            log.info("document splitted to array of words");

            Map<String, Integer> map = fillMapWithWords(arrayOfWords);
            log.info("added " + map.size() + " entries to map");

            printSortedMap(map);

        } catch (MalformedURLException e) {
            System.out.println(e);
            log.log(Level.SEVERE, String.valueOf(e));
        } catch (UnknownHostException e) {
            System.out.println(e);
            log.log(Level.SEVERE, String.valueOf(e));
        } catch (IOException e) {
            System.out.println(e);
            log.log(Level.SEVERE, String.valueOf(e));
        }
    }

    /**
     * Reads string from console
     * @return String value
     */
    public String readStringFromConsole() {
        Scanner inputUrlStringScanner = new Scanner(System.in);
        return inputUrlStringScanner.nextLine();
    }

    /**
     * Saves webpage with provided url
     * @param urlString
     * @return {@link File}
     * @throws IOException
     */
    public File saveWebPageToFile(String urlString) throws IOException {
        URL url = new URL(urlString);
        File file = new File(FILE_DIR + url.getHost() + FILE_EXTENSION);
        FileUtils.copyInputStreamToFile(url.openStream(), file);
        return file;
    }

    /**
     * Parses html content from {@link File} into {@link Document}
     * @param file
     * @return {@link Document}
     * @throws IOException
     */
    public Document parseHtmlFromFile(File file) throws IOException {
        return Jsoup.parse(file, "UTF-8");
    }

    /**
     * Splits {@link Document} into string array of words by regular expression
     * @param document
     * @param regexp
     * @return string array of words
     */
    public String[] splitDocumentByRegExp(Document document, String regexp) {
        return document.text().toLowerCase().split(regexp);
    }

    /**
     * Fills {@link HashMap} with unique words and their count
     * @param words
     * @return HashMap with pairs word:count
     */
    public Map<String, Integer> fillMapWithWords(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
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
     * Prints map as word=count in order of decreasing words appearances
     * @param map
     */
    public void printSortedMap(Map<String, Integer> map) {
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }
}
