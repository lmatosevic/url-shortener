package lm.shortener.model;

import lm.shortener.model.ShortenedUrl;
import lm.shortener.util.ServiceHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

public class ShortenedUrlTest {

    @Test
    public void creationInitialTest() {
        String shortUrl = ServiceHelper.generateShortUrlCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl(shortUrl, "http://www.test.com?id=12431&tab=home", "301");
        Assert.assertEquals("Short url code must be " + shortUrl, shortUrl, shortenedUrl.getShortUrlCode());
        Assert.assertEquals("Full url must be http://www.test.com?id=12431&tab=home",
                "http://www.test.com?id=12431&tab=home", shortenedUrl.getFullUrl());
        Assert.assertEquals("Number of visits must be 0", 0, shortenedUrl.getVisits());
    }

    @Test
    public void creationFullTest() {
        String shortUrl = ServiceHelper.generateShortUrlCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl(shortUrl, "http://www.test.com?id=12431&tab=home", "301", 123);
        Assert.assertEquals("Number of visits must be 123", "123", shortenedUrl.getVisitsString());
    }

    @Test
    public void visitsIncrementationTest() {
        String shortUrl = ServiceHelper.generateShortUrlCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl(shortUrl, "http://www.test.com?id=12431&tab=home", "301", 123);
        shortenedUrl.incrementVisits();
        Assert.assertEquals("Number of visits must be 124", "124", shortenedUrl.getVisitsString());
    }

    @Test
    public void redirectTypeCodeTest() {
        String shortUrl = ServiceHelper.generateShortUrlCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl(shortUrl, "http://www.test.com?id=12431&tab=home", "301", 123);
        Assert.assertEquals("Redirect type must be permanently", HttpServletResponse.SC_MOVED_PERMANENTLY,
                shortenedUrl.getRedirectTypeCode());
        shortenedUrl.setRedirectType("302");
        Assert.assertEquals("Redirect type must be temporarily", HttpServletResponse.SC_MOVED_TEMPORARILY,
                shortenedUrl.getRedirectTypeCode());
    }

}
