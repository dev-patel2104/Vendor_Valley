package com.group10.Enums;

public enum TrendingServiceQuery {
    SERVICE_ID("service_id"),
    TOTAL_BOOKINGS_FOR_SERVICE("totalBookingsForService"),
    SERVICE_NAME("service_name"),
    SERVICE_DESCRIPTION("service_description"),
    SERVICE_PRICE("service_price"),
    SERVICE_IMAGE("image");

    private final String value;

    TrendingServiceQuery(String value) {
        this.value = value;
    }

    public String getColumnName() {
        return value;
    }
}
