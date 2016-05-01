package lm.shortener.dao;

import lm.shortener.model.ShortenedUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of data access object which provides all operations for ShortenedUrl objects. Storage is csv file.
 *
 * @author Luka
 */
public class ShortenedUrlDaoImpl implements ModelDao<ShortenedUrl> {

    private CsvConnector csvConnector;

    /**
     * Constructor which initializes csv connector.
     *
     * @param dataPath File path to csv file which is used for storing shortened url informations.
     */
    public ShortenedUrlDaoImpl(String dataPath) {
        csvConnector = new CsvConnector(dataPath + "/shortenedUrl.csv");
    }

    /**
     * {@inheritDoc}
     */
    public ShortenedUrl find(String key) {
        ShortenedUrl shortenedUrl;
        try {
            String[] row = csvConnector.readRow(key, 0);
            int visits = Integer.parseInt(row[4]);
            shortenedUrl = new ShortenedUrl(row[0], row[1], row[2], row[3], visits);
        } catch (Exception e) {
            return null;
        }
        return shortenedUrl;
    }

    /**
     * {@inheritDoc}
     */
    public List<ShortenedUrl> findAll() {
        List<ShortenedUrl> urls = new ArrayList<>();
        try {
            List<String[]> rows = csvConnector.readRows();
            for (String[] row : rows) {
                urls.add(new ShortenedUrl(row[0], row[1], row[2], row[3], Integer.parseInt(row[4])));
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return urls;
    }

    /**
     * {@inheritDoc}
     */
    public boolean create(ShortenedUrl element) {
        try {
            String[] row = new String[]{element.getShortUrlCode(), element.getFullUrl(), element.getAccountId(),
                    element.getRedirectType(), element.getVisitsString()};
            csvConnector.createRow(row);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean update(ShortenedUrl element) {
        boolean success;
        try {
            String[] row = new String[]{element.getShortUrlCode(), element.getFullUrl(), element.getAccountId(),
                    element.getRedirectType(), element.getVisitsString()};
            success = csvConnector.updateRow(element.getShortUrlCode(), row);
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    /**
     * {@inheritDoc}
     */
    public boolean delete(ShortenedUrl element) {
        boolean success;
        try {
            success = csvConnector.deleteRow(element.getShortUrlCode());
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    /**
     * Finds all registered urls by user with provided account id.
     *
     * @param accountId Account id which was used to register urls.
     * @return List of all urls that are registerd by account with provided id.
     */
    public List<ShortenedUrl> findAllByAccountId(String accountId) {
        List<ShortenedUrl> matchingUrls = new ArrayList<>();
        for(ShortenedUrl url : findAll()) {
            if(url.getAccountId().equals(accountId)) {
                matchingUrls.add(url);
            }
        }
        return matchingUrls;
    }
}
