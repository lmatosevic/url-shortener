package lm.shortener.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class that provides bunch of configuration constants and methods used by servlets to do their work.
 *
 * @author Luka
 */
public class ServiceHelper {

    /* Directory location in which are stored files with accounts and urls */
    public static final String DATA_DIR = "/data";
    public static final String DEFAULT_REDIRECT_TYPE = "302";

    private static final Random RANDOM = new SecureRandom();
    private static final int PASSWORD_LENGTH = 8;
    private static final int URL_LENGTH = 6;
    private static final String LETTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXY";
    private static final String NUMBERS = "23456789";

    /**
     * Method that reads JSON string from input stream and converts it into JSON object.
     *
     * @param reader Input stream.
     * @return Object representation of JSON string.
     */
    public static JSONObject generateJson(Reader reader) {
        String jsonData = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                jsonData += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JSONObject(jsonData);
    }

    /**
     * Generates random password using provided letters and number. Length of password is also constant.
     *
     * @return Random password string.
     */
    public static String generatePassword() {
        String letters = LETTERS + NUMBERS;

        String password = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            password += letters.substring(index, index + 1);
        }
        return password;
    }

    /**
     * Generates shortened url code using only provided letters. This code is used to access full url via shortened one.
     * If this method generates following code: xCwqZj, then shortened url will be: http://my.url/short/xCwqZj.
     *
     * @return Shortened url code string.
     */
    public static String generateShortUrlCode() {
        String letters = LETTERS;

        String url = "";
        for (int i = 0; i < URL_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            url += letters.substring(index, index + 1);
        }
        return url;
    }
}
