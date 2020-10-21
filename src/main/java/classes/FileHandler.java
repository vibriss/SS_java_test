package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class FileHandler {

    private static final String FILE_DIR = "files/";

    private File file;

    public FileHandler createHtmlFile(String fileName) {
        this.file = new File(FILE_DIR + fileName + ".html");
        return this;
    }

    public void saveWebPageToFile(URL url) throws NullPointerException, IOException {
        if (this.file == null) {
            throw new NullPointerException("File doesn't exist");
        }

        FileWriter fileWriter = new FileWriter(this.file);

        BufferedReader pageReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

        while (true) {
            String line = pageReader.readLine();
            if (line == null) {
                break;
            }
            fileWriter.write(line);
        }

        pageReader.close();
    }

    public File getFile() {
        return this.file;
    }
}
