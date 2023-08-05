package com.group10.Enums;


/**
 * Enumeration representing query columns for retrieving user details by user ID.
 * This enum provides a set of values that correspond to various columns used in user detail queries.
 */
public enum GetUserByIdQueryColumns {

    /**
     * Represents the column name for the user ID.
     */
    USER_ID("user_id"),

    /**
     * Represents the column name for the first name of the user.
     */
    FIRST_NAME("first_name"),

    /**
     * Represents the column name for the last name of the user.
     */
    LAST_NAME("last_name"),

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
     * Represents the column name for the email address of the user.
     */
    EMAIL("email"),

    /**
     * Represents the column name for the mobile number of the user.
     */
    MOBILE("mobile"),

    /**
     * Represents the column name for indicating whether the user is a vendor or not.
     */
    IS_VENDOR("is_vendor"),

    /**
     * Represents the column name for the password of the user.
     */
    PASSWORD("password"),

    /**
     * Represents the column name for the user's role.
     */
    USER_ROLE("user_role"),

    /**
     * Represents the column name for the company name of a vendor user.
     */
    COMPANY_NAME("company_name"),

    /**
     * Represents the column name for the company email of a vendor user.
     */
    COMPANY_EMAIL("company_email"),

    /**
     * Represents the column name for the company registration number of a vendor user.
     */
    COMPANY_REGISTRATION_NUMBER("company_registration_number"),

    /**
     * Represents the column name for the company mobile number of a vendor user.
     */
    COMPANY_MOBILE("company_mobile"),

    /**
     * Represents the column name for the company street address of a vendor user.
     */
    COMPANY_STREET("company_street"),

    /**
     * Represents the column name for the company city of a vendor user.
     */
    COMPANY_CITY("company_city"),

    /**
     * Represents the column name for the company province of a vendor user.
     */
    COMPANY_PROVINCE("company_province"),

    /**
     * Represents the column name for the company country of a vendor user.
     */
    COMPANY_COUNTRY("company_country");

    private String columnName;

    /**
     * Constructs a new GetUserByIdQueryColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    GetUserByIdQueryColumns(String columnName) {
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
