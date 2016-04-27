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
                    throw new Exception();
                }
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("description", "Authorization failed. Invalid password provided.");
            }
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("description", "An error occured while trying to register shortened url.");
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
