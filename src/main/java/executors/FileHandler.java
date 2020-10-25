package executors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class is used for creating file filled with html code from webpage located at provided URL.
 */

public class FileHandler {

    /**
     * files location directory
     */
    private static final String FILE_DIR = "files/";

    private File file;

    /**
     * Method creates file with name of webpage, filling it line by line with html code of webpage.
      * @param url
     * input URL of webpage
     */
    public void saveWebPageToFile(URL url) {
        this.file = new File(FILE_DIR + url.getHost() + ".html");

        try (
            FileWriter fileWriter = new FileWriter(this.file);
            BufferedReader pageReader = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)
            )
        ) {
            while (true) {
                String line = pageReader.readLine();
                if (line == null) {
                    break;
                }
                fileWriter.write(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter for field "file"
     * @return file
     */
    public File getFile() {
        return this.file;
    }
}
