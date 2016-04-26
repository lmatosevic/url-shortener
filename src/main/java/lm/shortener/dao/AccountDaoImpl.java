package lm.shortener.dao;

import lm.shortener.model.Account;

import java.io.IOException;

public class AccountDaoImpl implements ModelDao<Account> {

    private static CsvConnector csvConnector = new CsvConnector("data/account.csv");

    public Account find(String key) {
        Account account;
        try {
            String[] row = csvConnector.readRow(key);
            account = new Account(row[0], row[1]);
        } catch (Exception e) {
            return null;
        }
        return account;
    }

    public boolean create(Account element) {
        try {
            String[] row = new String[]{element.getAccountId(), element.getPasswordHash()};
            csvConnector.createRow(row);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean update(Account element) {
        boolean success;
        try {
            String[] row = new String[]{element.getAccountId(), element.getPasswordHash(), element.getPasswordHash()};
            success = csvConnector.updateRow(element.getAccountId(), row);
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    public boolean delete(Account element) {
        boolean success;
        try {
            success = csvConnector.deleteRow(element.getAccountId());
        } catch (IOException e) {
            return false;
        }
        return success;
    }
}
