package com.group10.Constants;

/**
 * This class contains constant values used throughout the application.
 */
public class Constants {

    /**
     * Represents the duration of six hours in milliseconds.
     */
    public static final Integer SIXHOURSINMILLISECONDS = 6 * 60 * 60 * 1000;

    /**
     * Constant value representing a verification code that has expired.
     */
    public static final int VERIFICATIONCODEEXPIRED = 0;

    /**
     * Constant value representing a failure to insert a row into the database.
     */
    public static final int ROWNOTINSERTED = -1;

    /**
     * Constant value representing a user that already exists in the system.
     */
    public static final int USERALREADYEXISTS = -2;

    /**
     * Constant value representing a failure to insert a user into the database.
     */
    public static final int USERNOTINSERTED = -3;

    /**
     * Constant value representing a user that does not exist in the system.
     */
    public static final int USERDOESNTEXIST = -4;
    public static final int VERIFICATIONCODEBOUND = 900000;
    public static final int VARIATION = 100000;

    /**
     * Constant String representing the "price" attribute used in various contexts.
     */
    public static final String PRICE = "price";

    /**
     * Constant String representing the "ratings" attribute used in various contexts.
     */
    public static final String RATING = "ratings";

    /**
     * Constant String representing the "bookings" attribute used in various contexts.
     */
    public static final String BOOKINGS = "bookings";

    /**
     * Constant String representing the "category" attribute used in various contexts.
     */
    public static final String CATEGORY = "category";

    /**
     * Constant String representing the "location" attribute used in various contexts.
     */
    public static final String LOCATION = "location";

    /**
     * Constant Boolean value representing the ascending sorting order.
     */
    public static final Boolean ASC = true;

    /**
     * Constant Boolean value representing the descending sorting order.
     */
    public static final Boolean DESC = false;
}