package com.group10.Enums;


/**
 * Enumeration representing query columns for retrieving categories.
 * This enum provides a set of values that correspond to various columns used in category queries.
 */
public enum GetCategoriesQuery {

    /**
     * Represents the column name for the category ID in category details.
     */
    CATEGORY_ID("category_id"),

    /**
     * Represents the column name for the category name in category details.
     */
    CATEGORY_NAME("category_name"),

    /**
     * Represents the column name for the category description in category details.
     */
    CATEGORY_DESCRIPTION("category_description");

    private final String value;

    /**
     * Constructs a new GetCategoriesQuery enum with the specified column name.
     *
     * @param value The name of the query column.
     */
    GetCategoriesQuery(String value) {
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
