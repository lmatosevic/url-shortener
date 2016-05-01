package lm.shortener.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class StatisticServletTest extends ServiceMock {

    public AccountServlet accountServlet;
    public RegisterServlet registerServlet;
    public StatisticServlet statisticServlet;
    public ShortServlet shortServlet;
    public String password;
    public Map<String, Integer> urlVisits = new HashMap<>();

    @Before
    public void initializeServlet() throws ServletException, IOException {
        accountServlet = new AccountServlet();
        registerServlet = new RegisterServlet();
        statisticServlet = new StatisticServlet();
        shortServlet = new ShortServlet();

        initializeServlet(accountServlet);
        initializeServlet(registerServlet);
        initializeServlet(statisticServlet);
        initializeServlet(shortServlet);
        password = getPasswordFromResponse(accountServlet, "John");

        String shortUrlCode = registerNewUrl(registerServlet, "http://long.url.com/xyz", password);
        visitShortUrl(shortServlet, shortUrlCode);
        urlVisits.put("http://long.url.com/xyz", 1);

        shortUrlCode = registerNewUrl(registerServlet, "http://verylong.url.com/xyz/abc", password);
        visitShortUrl(shortServlet, shortUrlCode);
        visitShortUrl(shortServlet, shortUrlCode);
        visitShortUrl(shortServlet, shortUrlCode);
        urlVisits.put("http://verylong.url.com/xyz/abc", 3);

        shortUrlCode = registerNewUrl(registerServlet, "http://ultralong.url.com/xyz/abc/klm", password);
        visitShortUrl(shortServlet, shortUrlCode);
        visitShortUrl(shortServlet, shortUrlCode);
        urlVisits.put("http://ultralong.url.com/xyz/abc/klm", 2);
    }

    @After
    public void removeFiles() {
        File file = new File(TEST_PATH + "/account.csv");
        boolean success = file.delete();
        file = new File(TEST_PATH + "/shortenedUrl.csv");
        success = file.delete();
    }

    @Test
    public void getStatisticTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("");
        when(request.getHeader("Authorization")).thenReturn(password);
        when(request.getRequestURI()).thenReturn("localhost:8080/statistic/John");
        statisticServlet.doGet(request, response);
        String servletResponse = stringWriter.toString();
        String expectedStats = "";
        for (String url : urlVisits.keySet()) {
            expectedStats += "\"" + url + "\":\"" + urlVisits.get(url) + "\",";
        }
        expectedStats = "{" + expectedStats.substring(0, expectedStats.lastIndexOf(',')) + "}";
        Assert.assertEquals("Expected statistics", expectedStats, servletResponse);
    }
}
