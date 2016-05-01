package lm.shortener.service;

import lm.shortener.dao.AccountDaoImpl;
import lm.shortener.dao.ShortenedUrlDaoImpl;
import lm.shortener.model.ShortenedUrl;
import lm.shortener.util.ServiceHelper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet that processes request from user and generates statistic. Statistic is showing number of visits for every
 * registered url in api. Authorization header is required for getting the results.
 *
 * Allowed methods: GET
 * Input: -
 * Output: json {"some full url":"number of visits", "some full url 2":"number of visits", ...}
 * ContentType: application/json
 * Mapping: /statistic
 *
 * @author Luka
 */
public class StatisticServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDaoImpl accountDao = new AccountDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));
        ShortenedUrlDaoImpl shortenedUrlDao = new ShortenedUrlDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));

        JSONObject jsonResponse = new JSONObject();
        try {
            String password = request.getHeader("Authorization");
            if (password == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                if (accountDao.passwordExists(password)) {
                    List<ShortenedUrl> urls = shortenedUrlDao.findAll();
                    for (ShortenedUrl url : urls) {
                        if(!jsonResponse.has(url.getFullUrl())) {
                            jsonResponse.put(url.getFullUrl(), url.getVisitsString());
                        } else {
                            int visits = Integer.parseInt(jsonResponse.getString(url.getFullUrl()));
                            visits += url.getVisits();
                            jsonResponse.remove(url.getFullUrl());
                            jsonResponse.put(url.getFullUrl(), String.valueOf(visits));
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
