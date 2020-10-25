package executors;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Class is used for checking input string for valid URL and checking this URL for availability.
 */

public class UrlHandler {

    private static Logger log = Logger.getLogger(TextHandler.class.getName());

    /**
     * Method validates input string as correct URL using UrlValidator library. Next, if URL is valid, it checked for
     * host availability using InetAddress library.
     * @param  inputUrl             input String
     * @return url                  created object of URL
     * @throws UnknownHostException if host is not reachable
     * @throws IOException          if URL is not valid
     */
    public URL createURL(String inputUrl) throws UnknownHostException, IOException{
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(inputUrl)) {
            log.info(inputUrl + " is not a valid URL");
            throw new IOException(inputUrl + " is not a valid URL");
        }

        URL url = new URL(inputUrl);

        if (!InetAddress.getByName(url.getHost()).isReachable(1000)) {
            log.info("Host " + url.getHost() + " is not reachable");
            throw new UnknownHostException("Host " + url.getHost() + " is not reachable");
        }

        return url;
    }
}
