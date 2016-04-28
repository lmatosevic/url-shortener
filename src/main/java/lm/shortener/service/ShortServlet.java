package lm.shortener.service;

import lm.shortener.dao.ShortenedUrlDaoImpl;
import lm.shortener.model.ShortenedUrl;
import lm.shortener.util.ServiceHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShortServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShortenedUrlDaoImpl shortenedUrlDao = new ShortenedUrlDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));

        try {
            String uri = request.getRequestURI();
            String shortUrlCode = uri.substring(uri.lastIndexOf('/') + 1);
            ShortenedUrl shortenedUrl = shortenedUrlDao.find(shortUrlCode);
            if (shortenedUrl != null) {
                shortenedUrl.incrementVisits();
                shortenedUrlDao.update(shortenedUrl);
                response.setStatus(shortenedUrl.getRedirectTypeCode());
                response.setHeader("Location", response.encodeRedirectURL(shortenedUrl.getFullUrl()));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
