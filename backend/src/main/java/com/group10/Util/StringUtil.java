package com.group10.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public static java.sql.Date dateStringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.sql.Date dateInDB = new java.sql.Date(formatter.parse(dateStr).getTime());
            return dateInDB;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

