package lm.shortener.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.util.Random;

public class ServiceHelper {

    public static final String DATA_DIR = "/data";
    public static final String DEFAULT_REDIRECT_TYPE = "302";

    private static final Random RANDOM = new SecureRandom();
    private static final int PASSWORD_LENGTH = 8;
    private static final int URL_LENGTH = 6;

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

    public static String generatePassword() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

        String password = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            password += letters.substring(index, index + 1);
        }
        return password;
    }

    public static String generateShortUrlCode() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

        String url = "";
        for (int i = 0; i < URL_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            url += letters.substring(index, index + 1);
        }
        return url;
    }
}
