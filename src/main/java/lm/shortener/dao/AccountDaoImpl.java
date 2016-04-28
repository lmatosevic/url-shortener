package lm.shortener.dao;

import lm.shortener.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements ModelDao<Account> {

    private CsvConnector csvConnector;

    public AccountDaoImpl(String dataPath) {
        csvConnector = new CsvConnector(dataPath + "/account.csv");
    }

    public Account find(String key) {
        Account account;
        try {
            String[] row = csvConnector.readRow(key, 0);
            account = new Account(row[0], row[1], true);
        } catch (Exception e) {
            return null;
        }
        return account;
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<Account>();
        try {
            List<String[]> rows = csvConnector.readRows();
            for (String[] row : rows) {
                accounts.add(new Account(row[0], row[1], true));
            }
        } catch (Exception e) {
            return null;
        }
        return accounts;
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

    public Account findByPassword(String password) {
        Account account = new Account("", password, false);
        try {
            String[] row = csvConnector.readRow(account.getPasswordHash(), 1);
            account = new Account(row[0], row[1], true);
        } catch (Exception e) {
            return null;
        }
        return account;
    }

    public boolean passwordExists(String password) {
        return findByPassword(password) != null;
    }
}
