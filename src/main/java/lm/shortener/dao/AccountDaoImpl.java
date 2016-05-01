package lm.shortener.dao;

import lm.shortener.model.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of data access object which provides all operations for Account objects. Storage is csv file.
 *
 * @author Luka
 */
public class AccountDaoImpl implements ModelDao<Account> {

    private CsvConnector csvConnector;

    /**
     * Constructor which initializes csv connector.
     *
     * @param dataPath File path to csv file which is used for storing accounts informations.
     */
    public AccountDaoImpl(String dataPath) {
        csvConnector = new CsvConnector(dataPath + "/account.csv");
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
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

    /**
     * {@inheritDoc}
     */
    public boolean create(Account element) {
        try {
            String[] row = new String[]{element.getAccountId(), element.getPasswordHash()};
            csvConnector.createRow(row);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public boolean delete(Account element) {
        boolean success;
        try {
            success = csvConnector.deleteRow(element.getAccountId());
        } catch (IOException e) {
            return false;
        }
        return success;
    }

    /**
     * Additional method for searching objects by provided password string.
     *
     * @param password Password string of searched object.
     * @return Account object if found, null otherwise.
     */
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

    /**
     * Checks if account with provided password exists in storage.
     *
     * @param password Password stirng.
     * @return True if account exists, false otherwise.
     */
    public boolean passwordExists(String password) {
        return findByPassword(password) != null;
    }
}
