import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class WebPageParser {

    private static final String FILE_DIR = "files/";
    private static final String REG_EXP = "[\\s-,.!?\";:«»\\[\\]()\n\r\t]+";

    public static void main(String[] args) {

        try {
            System.out.println("Еnter page url as \"https://abc.com/\"");

            String inputUrl = new BufferedReader(new InputStreamReader(System.in)).readLine();

            UrlValidator urlValidator = new UrlValidator();

            if (!urlValidator.isValid(inputUrl)) {
                throw new IOException("Invalid URL");
            }

            URL url = new URL(inputUrl);

            if (!InetAddress.getByName(url.getHost()).isReachable(1000)) {
                throw new UnknownHostException();
            }

            String fileName = url.getHost();
            File file = new File(FILE_DIR + fileName + ".html");

            if (file.exists()) {
                file.delete();
            }

            FileWriter fileWriter = new FileWriter(file);

            BufferedReader pageReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            while (true) {
                String line = pageReader.readLine();
                if (line == null) {
                    break;
                }
                fileWriter.write(line);
            }

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

            for (HashMap.Entry pair : map.entrySet()) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }

        } catch (UnknownHostException e) {
            System.out.println("Host " + e.getMessage() + " is not reachable");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}