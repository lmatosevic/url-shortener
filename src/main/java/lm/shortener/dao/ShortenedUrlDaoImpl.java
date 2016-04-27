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
            String[] row = csvConnector.readRow(key);
            shortenedUrl = new ShortenedUrl(row[0], row[1]);
        } catch (Exception e) {
            return null;
        }
        return shortenedUrl;
    }

    public boolean create(ShortenedUrl element) {
        try {
            String[] row = new String[]{element.getShortUrl(), element.getFullUrl(), element.getVisitsString()};
            csvConnector.createRow(row);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean update(ShortenedUrl element) {
        boolean success;
        try {
            String[] row = new String[]{element.getShortUrl(), element.getFullUrl(), element.getVisitsString()};
            success = csvConnector.updateRow(element.getShortUrl(), row);
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    public boolean delete(ShortenedUrl element) {
        boolean success;
        try {
            success = csvConnector.deleteRow(element.getShortUrl());
        } catch (IOException e) {
            return false;
        }
        return success;
    }
}
