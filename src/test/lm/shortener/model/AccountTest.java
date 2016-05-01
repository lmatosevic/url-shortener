package lm.shortener.model;

import lm.shortener.util.ServiceHelper;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test
    public void creationWithHashTest() {
        Account account = new Account("John", "pass123", false);
        Assert.assertEquals("Account name must be John", "John", account.getAccountId());
        Assert.assertEquals("Account password must be hashed", "aafdc23870ecbcd3d557b6423a8982134e17927e",
                account.getPasswordHash());
    }

    @Test
    public void creationTWithoutHashTest() {
        String passwordHash = ServiceHelper.generatePassword();
        Account account = new Account("John", passwordHash, true);
        Assert.assertEquals("Account name must be John", "John", account.getAccountId());
        Assert.assertEquals("Account password must be hashed", passwordHash, account.getPasswordHash());
    }
}
