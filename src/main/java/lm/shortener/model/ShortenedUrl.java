package lm.shortener.model;

public class ShortenedUrl {

    private String shortUrl;
    private String fullUrl;
    private int visits;

    public ShortenedUrl(String shortUrl, String fullUrl) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.visits = 0;
    }

    public ShortenedUrl(String fullUrl, String shortUrl, int visits) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.visits = visits;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getVisits() {
        return visits;
    }

    public String getVisitsString() {
        return String.valueOf(visits);
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
}
