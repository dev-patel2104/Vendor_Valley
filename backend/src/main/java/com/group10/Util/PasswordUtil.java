package com.group10.Util;

public class PasswordUtil {
    /**
     * Checks if the given password is valid.
     *
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        return StringUtil.isNotNullAndNotEmpty(password) && password.length() > 7;
    }
}
