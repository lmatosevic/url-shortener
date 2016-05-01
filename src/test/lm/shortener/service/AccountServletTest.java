package lm.shortener.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.*;

public class AccountServletTest extends ServiceMock {

    public AccountServlet accountServlet;

    @Before
    public void initializeServlet() throws ServletException {
        accountServlet = new AccountServlet();
        initializeServlet(accountServlet);
    }

    @After
    public void removeFiles() {
        File file = new File(TEST_PATH + "/account.csv");
        boolean success = file.delete();
    }

    @Test
    public void createNewAccountSuccessTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"AccountId\":\"John\"}");
        accountServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertTrue("New user is created", servletResponse.
                contains("\"description\":\"Your account has been successfully created.\""));
    }

    @Test
    public void createNewAccountInvalidArgumentTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"Id\":\"123\"}");
        accountServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertTrue("New user is not created", servletResponse.equals("{}"));
    }

    @Test
    public void createNewAccountAlreadyExistsTest() throws Exception {
        StringWriter stringWriter = mockInputOutputStreams("{\"AccountId\":\"Jack\"}");
        accountServlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        Assert.assertTrue("New user is created", servletResponse.
                contains("\"description\":\"Your account has been successfully created.\""));

        stringWriter = mockInputOutputStreams("{\"AccountId\":\"Jack\"}");
        accountServlet.doPost(request, response);
        servletResponse = stringWriter.toString();
        Assert.assertTrue("New user is not created", servletResponse.
                contains("\"description\":\"Account with provided id already exists.\""));
    }
}
