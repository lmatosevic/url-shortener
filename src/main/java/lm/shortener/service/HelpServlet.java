package lm.shortener.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that returns content of jsp page index.jsp which contains instructions for using this api.
 *
 * Allowed methods: POST, GET
 * Input: -
 * Output: html page
 * ContentType: text/html
 * Mapping: /help
 *
 * @author Luka
 */
public class HelpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doBoth(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doBoth(request, response);
    }

    private void doBoth(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
