package com.group10.Enums;


/**
 * Enumeration representing query columns for retrieving reviews by service.
 * This enum provides a set of values that correspond to various columns used in review queries.
 */
public enum GetReviewsByServiceQueryColumns {

    /**
     * Represents the column name for the service ID associated with the review.
     */
    SERVICE_ID("service_id"),

    /**
     * Represents the column name for the user ID associated with the review.
     */
    USER_ID("user_id"),

    /**
     * Represents the column name for the booking ID associated with the review.
     */
    BOOKING_ID("booking_id"),

    /**
     * Represents the column name for the title of the review.
     */
    TITLE("title"),

    /**
     * Represents the column name for the comment text of the review.
     */
    COMMENT_TEXT("comment_text"),

    /**
     * Represents the column name for the date the review was created.
     */
    REVIEW_DATE("review_date"),

    /**
     * Represents the column name for the rating given in the review.
     */
    RATING("rating"),

    /**
     * Represents the column name for the name of the user who submitted the review.
     */
    NAME("name"),

    /**
     * Represents the column name for the city of the user who submitted the review.
     */
    CITY("city"),

    /**
     * Represents the column name for the country of the user who submitted the review.
     */
    COUNTRY("country");

    private final String columnName;

    /**
     * Constructs a new GetReviewsByServiceQueryColumns enum with the specified column name.
     *
     * @param columnName The name of the query column.
     */
    GetReviewsByServiceQueryColumns(String columnName) {
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
