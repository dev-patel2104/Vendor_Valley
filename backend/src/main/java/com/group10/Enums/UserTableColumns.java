package com.group10.Enums;


/**
 * Enumeration representing columns in the user table.
 * This enum provides a set of values that correspond to various columns used in user table queries.
 */
public enum UserTableColumns {

    /**
     * Represents the column name for the user ID.
     */
    USER_ID("user_id"),

    /**
     * Represents the column name for the last name of the user.
     */
    LAST_NAME("last_name"),

    /**
     * Represents the column name for the first name of the user.
     */
    FIRST_NAME("first_name"),

    /**
     * Represents the column name for the mobile number of the user.
     */
    MOBILE("mobile"),

    /**
     * Represents the column name for indicating whether the user is a vendor.
     */
    IS_VENDOR("is_vendor"),

    /**
     * Represents the column name for the email address of the user.
     */
    EMAIL("email"),

    /**
     * Represents the column name for the street address of the user.
     */
    STREET("street"),

    /**
     * Represents the column name for the city of the user.
     */
    CITY("city"),

    /**
     * Represents the column name for the province of the user.
     */
    PROVINCE("province"),

    /**
     * Represents the column name for the country of the user.
     */
    COUNTRY("country"),

    /**
     * Represents the column name for the password of the user.
     */
    PASSWORD("password");

    private final String columnName;

    /**
     * Constructs a new UserTableColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    UserTableColumns(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Retrieves the column name associated with the enum value.
     *
     * @return The column name.
     */
    public String getColumnName() {
        return columnName;
    }
}
