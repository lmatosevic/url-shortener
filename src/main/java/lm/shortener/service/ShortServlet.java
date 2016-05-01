package lm.shortener.service;

import lm.shortener.dao.ShortenedUrlDaoImpl;
import lm.shortener.model.ShortenedUrl;
import lm.shortener.util.ServiceHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that redirects user from short url to original url location. Short url code is representing virtual location
 * on server. Servlet reads last part of short url and uses code to find full url from storage and redirects user to
 * original url location with coresponding http redirect type code(301 or 302). Before redirecting, number of visits for
 * requested url is incremented.
 *
 * Allowed methods: GET
 * Input: -
 * Output: -
 * ContentType: -
 * Mapping: /short/*
 *
 * @author Luka
 */
public class ShortServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShortenedUrlDaoImpl shortenedUrlDao = new ShortenedUrlDaoImpl(getServletContext().getRealPath(ServiceHelper.DATA_DIR));

        try {
            String uri = request.getRequestURI();
            String shortUrlCode = uri.substring(uri.lastIndexOf("/short/") + 7);
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
