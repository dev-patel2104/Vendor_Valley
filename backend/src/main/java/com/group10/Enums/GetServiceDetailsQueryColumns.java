package com.group10.Enums;


/**
 * Enumeration representing query columns for retrieving service details.
 * This enum provides a set of values that correspond to various columns used in service detail queries.
 */
public enum GetServiceDetailsQueryColumns {

    /**
     * Represents the column name for the service ID.
     */
    SERVICE_ID("service_id"),

    /**
     * Represents the column name for the user ID associated with the service.
     */
    USER_ID("user_id"),

    /**
     * Represents the column name for the name of the service.
     */
    SERVICE_NAME("service_name"),

    /**
     * Represents the column name for the description of the service.
     */
    SERVICE_DESCRIPTION("service_description"),

    /**
     * Represents the column name for the price of the service.
     */
    SERVICE_PRICE("service_price"),

    /**
     * Represents the column name for the email of the company providing the service.
     */
    COMPANY_EMAIL("company_email");

    private final String columnName;

    /**
     * Constructs a new GetServiceDetailsQueryColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    GetServiceDetailsQueryColumns(String columnName) {
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
