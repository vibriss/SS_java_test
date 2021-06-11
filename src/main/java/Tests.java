import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Example tests
 */
public class Tests {

    private static WebPageParser webPageParser;
    private static String[] testArray;
    private static Map<String, Integer> testMap;

    @BeforeAll
    public static void prepare() {
        webPageParser = new WebPageParser();
        testArray = new String[] {"астролябия", "cекстан", "компас", "навигация", "компас"};
        testMap = Map.of("астролябия", 1, "cекстан", 1, "компас", 2, "навигация", 1);
    }

    @Test
    public void givenTestArray_whenFillMapWithWords_thanOk() {
        Map<String, Integer> actualMap = webPageParser.fillMapWithWords(testArray);
        Assertions.assertEquals(testMap, actualMap);
    }

    @Test
    public void givenMalformedUrlString_whenSaveWebPageToFile_thenThrowException(){
        String malformedUrlString = "gibberish";
        Assertions.assertThrows(MalformedURLException.class, () -> webPageParser.saveWebPageToFile(malformedUrlString));
    }
}
