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

public class AccountServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDaoImpl accountDao = new AccountDaoImpl(getServletContext().getRealPath("/data"));
        JSONObject jsonAccount = ServiceHelper.generateJson(request.getReader());

        JSONObject jsonResponse = new JSONObject();
        try {
            String accountId = jsonAccount.getString("AccountId");
            jsonResponse.put("success", true);
            if (accountDao.find(accountId) == null) {
                String password = ServiceHelper.generatePassword();
                Account account = new Account(accountId, password);
                if (accountDao.create(account)) {
                    jsonResponse.put("description", "Your account has been successfully created.");
                    jsonResponse.put("password", password);
                } else {
                    jsonResponse.put("description", "Account cannot be created. Problem with writing.");
                }
            } else {
                jsonResponse.put("description", "Account with provided id already exists.");
            }
        } catch(Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("description", "Account cannot be created. Invalid input parameters.");
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
