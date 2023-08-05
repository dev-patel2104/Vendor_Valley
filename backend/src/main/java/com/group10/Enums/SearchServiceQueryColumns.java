package com.group10.Enums;


/**
 * Enumeration representing query columns for searching services.
 * This enum provides a set of values that correspond to various columns used in service search queries.
 */
public enum SearchServiceQueryColumns {

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
     * Represents the column name for the categories associated with the service.
     */
    CATEGORIES("categories"),

    /**
     * Represents the column name for the street address of the company offering the service.
     */
    COMPANY_STREET("company_street"),

    /**
     * Represents the column name for the city of the company offering the service.
     */
    COMPANY_CITY("company_city"),

    /**
     * Represents the column name for the province of the company offering the service.
     */
    COMPANY_PROVINCE("company_province"),

    /**
     * Represents the column name for the country of the company offering the service.
     */
    COMPANY_COUNTRY("company_country"),

    /**
     * Represents the column name for the average rating of the service.
     */
    AVERAGE_RATING("avgRating"),

    /**
     * Represents the column name for the total number of bookings for the service.
     */
    TOTAL_BOOKINGS("totalBookings");

    private final String columnName;

    /**
     * Constructs a new SearchServiceQueryColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    SearchServiceQueryColumns(String columnName) {
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
