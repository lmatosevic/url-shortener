package lm.shortener.service;

import lm.shortener.dao.AccountDaoImpl;
import lm.shortener.dao.ShortenedUrlDaoImpl;
import lm.shortener.model.Account;
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
 * registered url in api. Authorization header is required for getting the results. Account for which statistic is
 * generated must be provided as part of uri, e.g. /statistic/John will generate statistic for all urls registered by
 * account with id "John".
 * <p>
 * Allowed methods: GET
 * Input: -
 * Output: json {"some full url":"number of visits", "some full url 2":"number of visits", ...}
 * ContentType: application/json
 * Mapping: /statistic/*
 *
 * @author Luka
 */
public class StatisticServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataDir = getServletContext().getInitParameter(ServiceHelper.DATA_DIR_PARAM);
        AccountDaoImpl accountDao = new AccountDaoImpl(dataDir);
        ShortenedUrlDaoImpl shortenedUrlDao = new ShortenedUrlDaoImpl(dataDir);

        JSONObject jsonResponse = new JSONObject();
        try {
            String password = request.getHeader("Authorization");
            if (password == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                String uri = request.getRequestURI();
                String accountId = uri.substring(uri.lastIndexOf("/statistic/") + 11);
                Account account = accountDao.find(accountId);
                if (accountDao.passwordExists(password) && account != null) {
                    List<ShortenedUrl> urls = shortenedUrlDao.findAllByAccountId(account.getAccountId());
                    for (ShortenedUrl url : urls) {
                        if (!jsonResponse.has(url.getFullUrl())) {
                            jsonResponse.put(url.getFullUrl(), url.getVisitsString());
                        } else {
                            int visits = Integer.parseInt(jsonResponse.getString(url.getFullUrl()));
                            visits += url.getVisits();
                            jsonResponse.remove(url.getFullUrl());
                            jsonResponse.put(url.getFullUrl(), String.valueOf(visits));
                        }
                    }
                } else {
                    if (account == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
