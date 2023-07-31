package com.group10.Enums;

public enum GetCategoriesQuery {
    CATEGORY_ID("category_id"),
    CATEGORY_NAME("category_name"),
    CATEGORY_DESCRIPTION("category_description");

    private final String value;

    GetCategoriesQuery(String value) {
        this.value = value;
    }

    public String getColumnName() {
        return value;
    }
}
