package com.group10.Enums;


/**
 * Enumeration representing query columns for retrieving booking details.
 * This enum provides a set of values that correspond to various columns used in booking queries.
 */
public enum GetBookingDetailsQueryColumns {

    /**
     * Represents the column name for the service name in booking details.
     */
    SERVICE_NAME("service_name"),

    /**
     * Represents the column name for the booking ID in booking details.
     */
    BOOKING_ID("booking_id"),

    /**
     * Represents the column name for the booking date in booking details.
     */
    BOOKING_DATE("booking_date"),

    /**
     * Represents the column name for the start date in booking details.
     */
    START_DATE("start_date"),

    /**
     * Represents the column name for the end date in booking details.
     */
    END_DATE("end_date"),

    /**
     * Represents the column name for the booking status in booking details.
     */
    BOOKING_STATUS("booking_status"),

    /**
     * Represents the column name for the service ID in booking details.
     */
    SERVICE_ID("service_id");

    private final String columnName;

    /**
     * Constructs a new GetBookingDetailsQueryColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    GetBookingDetailsQueryColumns(String columnName) {
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
