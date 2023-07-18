package com.group10.Enums;

public enum GetServiceDetailsQueryColumns {
    SERVICE_ID("service_id"),
    USER_ID("user_id"),
    SERVICE_NAME("service_name"),
    SERVICE_DESCRIPTION("service_description"),
    SERVICE_PRICE("service_price"),
    COMPANY_EMAIL("company_email");

    private final String columnName;

    GetServiceDetailsQueryColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
} 
