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

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDaoImpl accountDao = new AccountDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));
        ShortenedUrlDaoImpl shortenedUrlDao = new ShortenedUrlDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));
        JSONObject jsonUrl = ServiceHelper.generateJson(request.getReader());

        JSONObject jsonResponse = new JSONObject();
        try {
            String password = request.getHeader("Authorization");
            if (password == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                if (accountDao.passwordExists(password)) {
                    String url = jsonUrl.getString("url");
                    String redirectType = ServiceHelper.DEFAULT_REDIRECT_TYPE;
                    if (jsonUrl.has("redirectType")) {
                        redirectType = jsonUrl.getString("redirectType");
                    }
                    String shortUrlCode;
                    do {
                        shortUrlCode = ServiceHelper.generateShortUrlCode();
                    } while (shortenedUrlDao.find(shortUrlCode) != null);
                    ShortenedUrl shortenedUrl = new ShortenedUrl(shortUrlCode, url, redirectType);
                    if (shortenedUrlDao.create(shortenedUrl)) {
                        String currentUrl = request.getRequestURL().toString();
                        String rootUrl = currentUrl.substring(0, currentUrl.lastIndexOf("/"));
                        jsonResponse.put("shortUrl", rootUrl + "/short/" + shortUrlCode);
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
