package lm.shortener.service;

import lm.shortener.dao.AccountDaoImpl;
import lm.shortener.model.Account;
import lm.shortener.util.ServiceHelper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that processes request from user and creates a new account. Then it returns generated password which can be
 * used for further authentication in api via heder attribute "Authorization". Password is returned only if creating new
 * account was successful.
 * <p>
 * Allowed methods: POST
 * Input: json {"AccountId":"someIdString"}
 * Output: json {"success": true|false, "description":"...", "password":"qwtr322c"}
 * ContentType: application/json
 * Mapping: /account
 *
 * @author Luka
 */
public class AccountServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDaoImpl accountDao = new AccountDaoImpl(getServletContext().getInitParameter(ServiceHelper.DATA_DIR_PARAM));
        JSONObject jsonAccount = ServiceHelper.generateJson(request.getReader());

        JSONObject jsonResponse = new JSONObject();
        try {
            String accountId = jsonAccount.getString("AccountId");
            if (accountDao.find(accountId) == null) {
                String password = ServiceHelper.generatePassword();
                Account account = new Account(accountId, password, false);
                if (accountDao.create(account)) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("description", "Your account has been successfully created.");
                    jsonResponse.put("password", password);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("description", "Account with provided id already exists.");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
