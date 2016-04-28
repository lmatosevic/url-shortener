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
                        jsonResponse.put(url.getFullUrl(), url.getVisitsString());
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
