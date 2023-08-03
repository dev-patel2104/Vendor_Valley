package com.group10.Enums;

public enum VendorDashboardInfoQuery {
    
    USER_ID("user_id"),
    BOOKING_STATUS("booking_status"),
    START_DATE("start_date"),
    END_DATE("end_date"),
    BOOKING_DATE("booking_date");

    private final String columnName;

    VendorDashboardInfoQuery(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
