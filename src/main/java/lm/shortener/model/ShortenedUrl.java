package lm.shortener.model;

import javax.servlet.http.HttpServletResponse;

/**
 * Class that represents shortened url object model.
 *
 * @author Luka
 */
public class ShortenedUrl {

    private String shortUrlCode;
    private String fullUrl;
    private String redirectType;
    private int visits;

    /**
     * Constructor that initializes properties and sets number of visits to zero.
     *
     * @param shortUrlCode Short url code.
     * @param fullUrl Url that is shortened.
     * @param redirectType Http return code, two possibilities 301 or 302.
     */
    public ShortenedUrl(String shortUrlCode, String fullUrl, String redirectType) {
        this.fullUrl = fullUrl;
        this.shortUrlCode = shortUrlCode;
        this.redirectType = redirectType;
        this.visits = 0;
    }

    /**
     * Constructor that initializes properties.
     *
     * @param shortUrlCode Short url code.
     * @param fullUrl Url that is shortened.
     * @param redirectType Http return code, two possibilities 301 or 302.
     * @param visits Number of visits.
     */
    public ShortenedUrl(String shortUrlCode, String fullUrl, String redirectType, int visits) {
        this.fullUrl = fullUrl;
        this.shortUrlCode = shortUrlCode;
        this.redirectType = redirectType;
        this.visits = visits;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrlCode() {
        return shortUrlCode;
    }

    public void setShortUrlCode(String shortUrl) {
        this.shortUrlCode = shortUrl;
    }

    public int getVisits() {
        return visits;
    }

    public String getVisitsString() {
        return String.valueOf(visits);
    }

    public String getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(String redirectType) {
        this.redirectType = redirectType;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public void incrementVisits() {
        this.visits++;
    }

    /**
     * Returns http redirect code type from class HttpServletResponse, moved permanently or temporarily.
     *
     * @return HttpServletResponse redirect code.
     */
    public int getRedirectTypeCode() {
        if (redirectType.equals("301")) {
            return HttpServletResponse.SC_MOVED_PERMANENTLY;
        } else if (redirectType.equals("302")) {
            return HttpServletResponse.SC_MOVED_TEMPORARILY;
        } else {
            return HttpServletResponse.SC_MOVED_TEMPORARILY;
        }
    }
}
