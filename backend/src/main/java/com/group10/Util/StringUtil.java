package com.group10.Util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class StringUtil {

    /**
     * Checks if a string is not null and not empty.
     *
     * @param str The string to check
     * @return true if the string is not null and not empty, false otherwise
     */
    public static boolean isNotNullAndNotEmpty(String str) {
        log.debug("Checking if string is not null and not empty: {}", str);
        return str != null && str.length() > 0;
    }

    /**
     * Converts a date string in "yyyy-MM-dd" format to a java.sql.Date object.
     *
     * @param dateStr The date string to convert
     * @return The converted java.sql.Date object
     * @throws IllegalArgumentException If the date string is not in the correct format
     */
    public static java.sql.Date dateStringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.sql.Date dateInDB = new java.sql.Date(formatter.parse(dateStr).getTime());
            log.debug("Converted date string '{}' to date: {}", dateStr, dateInDB);
            return dateInDB;
        } catch (ParseException e) {
            log.error("Error parsing date string '{}'", dateStr, e);
            throw new RuntimeException(e);
        }
    }
}

