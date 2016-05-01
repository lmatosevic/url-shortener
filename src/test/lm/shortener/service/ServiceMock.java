package lm.shortener.service;

import lm.shortener.util.ServiceHelper;
import org.mockito.Mockito;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ServiceMock extends Mockito {

    public static final String TEST_PATH = "./src/test";

    public HttpServletRequest request = mock(HttpServletRequest.class);
    public HttpServletResponse response = mock(HttpServletResponse.class);

    public void initializeServlet(HttpServlet servlet) throws ServletException {
        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(context);
        when(context.getRealPath(ServiceHelper.DATA_DIR)).thenReturn(TEST_PATH);
        servlet.init(config);
    }

    public String getPasswordFromResponse(AccountServlet servlet, String name) throws ServletException, IOException {
        StringWriter stringWriter = mockInputOutputStreams("{\"AccountId\":\"" + name + "\"}");
        servlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        int index = servletResponse.indexOf("password");
        return servletResponse.substring(index + 11, index + 19);
    }

    public String registerNewUrl(RegisterServlet servlet, String url, String password) throws IOException, ServletException {
        StringWriter stringWriter = mockInputOutputStreams("{\"url\":\"" + url + "\"}");
        when(request.getHeader("Authorization")).thenReturn(password);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/register"));
        servlet.doPost(request, response);
        String servletResponse = stringWriter.toString();
        return servletResponse.substring(13, servletResponse.lastIndexOf('"'));
    }

    public void visitShortUrl(ShortServlet servlet, String shortUrlCode) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("http://localhost:8080/short/" + shortUrlCode);
        servlet.doGet(request, response);
    }

    public StringWriter mockInputOutputStreams(String inputString) throws IOException {
        StringReader stringReader = new StringReader(inputString);
        BufferedReader br = new BufferedReader(stringReader);
        when(request.getReader()).thenReturn(br);
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(pw);
        return stringWriter;
    }
}
