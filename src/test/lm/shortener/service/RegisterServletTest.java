package lm.shortener.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.*;

public class RegisterServletTest extends ServiceMock {

    public AccountServlet accountServlet;
    public RegisterServlet registerServlet;
    public String password;

    @Before
    public void initializeServlet() throws ServletException, IOException {
        accountServlet = new AccountServlet();
        registerServlet = new RegisterServlet();
        initializeServlet(accountServlet);
        initializeServlet(registerServlet);
        password = getPasswordFromResponse(accountServlet, "John");
    }

    @After
    public void removeFiles() {
        File file = new File(TEST_PATH + "/account.csv");
        boolean success = file.delete();
        file = new File(TEST_PATH + "/shortenedUrl.csv");
        success = file.delete();
    }

    @Test
    public void registerNewUrlDefaultTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"url\":\"http://www.test.com?id=12431&tab=home\"}");
        when(request.getHeader("Authorization")).thenReturn(password);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        registerServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertTrue("New short url is registered", servletResponse.
                contains("\"shortUrl\":\"http://localhost:8080/short/"));
    }

    @Test
    public void registerNewUrlNoAuthorizationTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"url\":\"http://www.test.com?id=12431&tab=home\"}");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        registerServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertFalse("New short url is not registered", servletResponse.
                contains("\"shortUrl\":\"http://localhost:8080/short/"));
    }

    @Test
    public void registerNewUrlBadRequstTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"wrongParam\":\"123\"}");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        when(request.getHeader("Authorization")).thenReturn(password);
        registerServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertFalse("New short url is not registered", servletResponse.
                contains("\"shortUrl\":\"http://localhost:8080/short/"));
    }
}
