package com.group10.Enums;


/**
 * Enumeration representing columns in the vendor dashboard information query.
 * This enum provides a set of values that correspond to various columns used in vendor dashboard information queries.
 */
public enum VendorDashboardInfoQuery {

    /**
     * Represents the column name for the user ID.
     */
    USER_ID("user_id"),

    /**
     * Represents the column name for the booking status.
     */
    BOOKING_STATUS("booking_status"),

    /**
     * Represents the column name for the start date of a booking.
     */
    START_DATE("start_date"),

    /**
     * Represents the column name for the end date of a booking.
     */
    END_DATE("end_date"),

    /**
     * Represents the column name for the booking date.
     */
    BOOKING_DATE("booking_date");

    private final String columnName;

    /**
     * Constructs a new VendorDashboardInfoQuery enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    VendorDashboardInfoQuery(String columnName) {
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
