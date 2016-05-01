package lm.shortener.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that represents an account object model.
 *
 * @author Luka
 */
public class Account {

    private String accountId;
    private String passwordHash;

    /**
     * Constructor which initializes properties and generates password hash if needed.
     *
     * @param accountId Users account unique name.
     * @param password Users password or password hash used for authenticating user in requests.
     * @param isHashPassword True - provided password is allready hashed, false - provided password is plain text needs
     *                       to be hashed.
     */
    public Account(String accountId, String password, boolean isHashPassword) {
        this.accountId = accountId;
        if(!isHashPassword) {
            this.passwordHash = getSecurePassword(password);
        } else {
            this.passwordHash = password;
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Private method that generates password hash from password string. Algoritham used is SHA-1 which generates 160-bit
     * or 40 characters long password hash.
     *
     * @param passwordToHash Plain password string.
     * @return Password hash with 40 characters long string.
     */
    private static String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
