package com.group10.Enums;
/**
 * Enum representing query columns for getting booking details.
 */
public enum GetBookingDetailsQueryColumns
{
    SERVICE_NAME("service_name"),
    BOOKING_ID("booking_id"),
    BOOKING_DATE("booking_date"),
    START_DATE("start_date"),
    END_DATE("end_date"),
    BOOKING_STATUS("booking_status"),

    SERVICE_ID("service_id");

    private final String columnName;

    /**
     * Constructs a GetBookingDetailsQueryColumns enum with the specified column name.
     *
     * @param columnName the name of the query column
     */
    GetBookingDetailsQueryColumns(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Retrieves the column name associated with the enum value.
     *
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }
}
