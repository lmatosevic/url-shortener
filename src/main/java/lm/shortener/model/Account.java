package lm.shortener.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Account {

    private String accountId;
    private String passwordHash;

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
