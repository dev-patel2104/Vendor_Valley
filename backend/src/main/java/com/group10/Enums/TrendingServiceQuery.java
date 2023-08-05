package com.group10.Enums;


/**
 * Enumeration representing query columns for trending services.
 * This enum provides a set of values that correspond to various columns used in trending service queries.
 */
public enum TrendingServiceQuery {

    /**
     * Represents the column name for the service ID.
     */
    SERVICE_ID("service_id"),

    /**
     * Represents the column name for the total number of bookings for the service.
     */
    TOTAL_BOOKINGS_FOR_SERVICE("totalBookingsForService"),

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
     * Represents the column name for the image associated with the service.
     */
    SERVICE_IMAGE("image");

    private final String value;

    /**
     * Constructs a new TrendingServiceQuery enum with the specified column name.
     *
     * @param value The name of the query column.
     */
    TrendingServiceQuery(String value) {
        this.value = value;
    }

    /**
     * Retrieves the column name associated with the enum value.
     *
     * @return The column name.
     */
    public String getColumnName() {
        return value;
    }
}