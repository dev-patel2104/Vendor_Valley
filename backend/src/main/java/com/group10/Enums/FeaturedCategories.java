package com.group10.Enums;


/**
 * Enumeration representing featured categories and their associated attributes.
 * This enum provides a set of values that correspond to various attributes related to featured categories.
 */
public enum FeaturedCategories {

    /**
     * Represents the total number of services in a category.
     */
    TOTAL_SERVICES("totalServices"),

    /**
     * Represents the ID of a category.
     */
    CATEGORY_ID("category_id"),

    /**
     * Represents the name of a category.
     */
    CATEGORY_NAME("category_name"),

    /**
     * Represents the description of a category.
     */
    CATEGORY_DESCRIPTION("category_description"),

    /**
     * Represents the image associated with a category.
     */
    CATEGORY_IMAGE("category_image");

    private final String value;

    /**
     * Constructs a new FeaturedCategories enum with the given value.
     *
     * @param value The value associated with the enum element.
     */
    FeaturedCategories(String value) {
        this.value = value;
    }

    /**
     * Gets the value associated with the enum element.
     *
     * @return The value associated with the enum element.
     */
    public String getColumnName() {
        return value;
    }
}
