package com.group10.Enums;

public enum GetReviewsByServiceQueryColumns {
    SERVICE_ID("service_id"),
    USER_ID("user_id"),
    BOOKING_ID("booking_id"),
    TITLE("title"),
    COMMENT_TEXT("comment_text"),
    REVIEW_DATE("review_date"),
    RATING("rating"),
    NAME("name"),
    CITY("city"),
    COUNTRY("country");

    private final String columnName;

    GetReviewsByServiceQueryColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
