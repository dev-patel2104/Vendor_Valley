package com.group10.Enums;

public enum FeaturedCategories {
    TOTAL_SERVICES("totalServices"),
    CATEGORY_ID("category_id"),
    CATEGORY_NAME("category_name"),
    CATEGORY_DESCRIPTION("category_description"),
    CATEGORY_IMAGE("category_image");

    private final String value;

    FeaturedCategories(String value) {
        this.value = value;
    }

    public String getColumnName() {
        return value;
    }
}
