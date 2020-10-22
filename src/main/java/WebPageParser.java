import classes.FileHandler;
import classes.ParseText;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class WebPageParser {

    public static void main(String[] args) {

        try {
            System.out.println("Еnter page url as \"https://abc.com/\"");

            BufferedReader inputUrlReader = new BufferedReader(new InputStreamReader(System.in));
            String inputUrl = inputUrlReader.readLine();
            inputUrlReader.close();

            UrlValidator urlValidator = new UrlValidator();

            if (!urlValidator.isValid(inputUrl)) {
                throw new IOException("Invalid URL");
            }

            URL url = new URL(inputUrl);

            if (!InetAddress.getByName(url.getHost()).isReachable(1000)) {
                throw new UnknownHostException("Host is not reachable");
            }

            String fileName = url.getHost();

            FileHandler fileHandler = new FileHandler();

            fileHandler.createHtmlFile(fileName).saveWebPageToFile(url);

            ParseText parseText = new ParseText();
            Map<String, Integer> map = parseText.parseHtmlFromFile(fileHandler.getFile());
            for (HashMap.Entry pair : map.entrySet()) {
                System.out.println(pair.getKey() + " " + pair.getValue());
            }

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}