import executors.FileHandler;
import executors.TextHandler;
import executors.UrlHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Class is used for counting unique words on the webpage.
 */
public class WebPageParser {

    private static Logger log = Logger.getLogger(WebPageParser.class.getName());

    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(
                    WebPageParser.class.getResourceAsStream("/logging.properties")
            );

            System.out.println("Еnter page url as \"https://abc.com/\"");

            BufferedReader inputUrlReader = new BufferedReader(new InputStreamReader(System.in));
            String inputUrl = inputUrlReader.readLine();
            inputUrlReader.close();
            log.info("entered url string \"" + inputUrl + "\"");

            URL url = new UrlHandler().createURL(inputUrl);

            FileHandler fileHandler = new FileHandler();
            fileHandler.saveWebPageToFile(url);

            TextHandler textHandler = new TextHandler();
            Map<String, Integer> map = textHandler.parseHtmlFromFile(fileHandler.getFile());
            textHandler.print(map);

        } catch (UnknownHostException e) {
            System.out.println("Host is not reachable " + e.getMessage());
            log.log(Level.SEVERE, "Exception: ", e);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}