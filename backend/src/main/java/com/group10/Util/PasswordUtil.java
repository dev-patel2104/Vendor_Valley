package com.group10.Util;

public class PasswordUtil {

    //todo: can add encryption logic here, if needed

    public static boolean isValidPassword(String password) {
        return StringUtil.isNotNullAndNotEmpty(password) && password.length() > 7;
    }
}
