package lm.shortener.dao;

import lm.shortener.model.ShortenedUrl;

import java.io.IOException;

public class ShortenedUrlDaoImpl implements ModelDao<ShortenedUrl> {

    private CsvConnector csvConnector;

    public ShortenedUrlDaoImpl(String dataPath) {
        csvConnector = new CsvConnector(dataPath + "/shortenedUrl.csv");
    }

    public ShortenedUrl find(String key) {
        ShortenedUrl shortenedUrl;
        try {
            String[] row = csvConnector.readRow(key, 0);
            int visits = Integer.parseInt(row[3]);
            shortenedUrl = new ShortenedUrl(row[0], row[1], row[2], visits);
        } catch (Exception e) {
            return null;
        }
        return shortenedUrl;
    }

    public boolean create(ShortenedUrl element) {
        try {
            String[] row = new String[]{element.getShortUrlCode(), element.getFullUrl(), element.getRedirectType(),
                    element.getVisitsString()};
            csvConnector.createRow(row);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean update(ShortenedUrl element) {
        boolean success;
        try {
            String[] row = new String[]{element.getShortUrlCode(), element.getFullUrl(), element.getRedirectType(),
                    element.getVisitsString()};
            success = csvConnector.updateRow(element.getShortUrlCode(), row);
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    public boolean delete(ShortenedUrl element) {
        boolean success;
        try {
            success = csvConnector.deleteRow(element.getShortUrlCode());
        } catch (IOException e) {
            return false;
        }
        return success;
    }
}
