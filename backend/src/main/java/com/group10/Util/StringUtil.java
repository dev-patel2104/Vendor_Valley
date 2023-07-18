package com.group10.Util;

public class StringUtil {

    /**
     * Checks if a string is not null and not empty.
     *
     * @param str The string to check
     * @return true if the string is not null and not empty, false otherwise
     */
    public static boolean isNotNullAndNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
}

