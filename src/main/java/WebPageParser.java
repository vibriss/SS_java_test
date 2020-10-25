import executors.FileHandler;
import executors.TextHandler;
import executors.UrlHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Class is used for counting unique words of webpage.
 */
public class WebPageParser {

    public static void main(String[] args) {

        try {
            System.out.println("Еnter page url as \"https://abc.com/\"");

            BufferedReader inputUrlReader = new BufferedReader(new InputStreamReader(System.in));
            String inputUrl = inputUrlReader.readLine();
            inputUrlReader.close();

            URL url = new UrlHandler().createURL(inputUrl);

            FileHandler fileHandler = new FileHandler();
            fileHandler.saveWebPageToFile(url);

            TextHandler textHandler = new TextHandler();
            Map<String, Integer> map = textHandler.parseHtmlFromFile(fileHandler.getFile());
            textHandler.print(map);

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}