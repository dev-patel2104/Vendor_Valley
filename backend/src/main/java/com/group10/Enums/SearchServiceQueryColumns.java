package com.group10.Enums;

public enum SearchServiceQueryColumns {
    SERVICE_ID("service_id"),
    USER_ID("user_id"),
    SERVICE_NAME("service_name"),
    SERVICE_DESCRIPTION("service_description"),
    SERVICE_PRICE("service_price"),
    CATEGORIES("categories"),
    COMPANY_STREET("company_street"),
    COMPANY_CITY("company_city"),
    COMPANY_PROVINCE("company_province"),
    COMPANY_COUNTRY("company_country"),
    AVERAGE_RATING("avgRating"),
    TOTAL_BOOKINGS("totalBookings");

    private final String columnName;

    SearchServiceQueryColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}