package com.group10.UtilTests;

import com.group10.Util.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordUtilTest {

    @Test
    public void isValidPasswordTest() {
        String validPassword1 = "boon@dal_MACS";
        String validPassword2 = "12345678";
        String invalidPassword1 = "boon";
        String invalidPassword2 = null;
        String invalidPassword3 = "";

        assertTrue(PasswordUtil.isValidPassword(validPassword1));
        assertTrue(PasswordUtil.isValidPassword(validPassword2));

        assertFalse(PasswordUtil.isValidPassword(invalidPassword1));
        assertFalse(PasswordUtil.isValidPassword(invalidPassword2));
        assertFalse(PasswordUtil.isValidPassword(invalidPassword3));
    }
}
