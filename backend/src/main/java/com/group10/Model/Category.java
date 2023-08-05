package com.group10.Model;

public class Category
{
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
    private int totalServices;
    private String base64Image;

    /**
     * Get the base64 encoded image associated with the category.
     *
     * @return The base64 encoded image.
     */
    public String getBase64Image() {
        return base64Image;
    }

    /**
     * Set the base64 encoded image associated with the category.
     *
     * @param base64Image The base64 encoded image to set.
     */
    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    /**
     * Get the category ID.
     *
     * @return The category ID.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Get the category name.
     *
     * @return The category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Get the category description.
     *
     * @return The category description.
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    /**
     * Get the total number of services in this category.
     *
     * @return The total number of services.
     */
    public int getTotalServices() {
        return totalServices;
    }

    /**
     * Set the category ID.
     *
     * @param categoryId The category ID to set.
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Set the category name.
     *
     * @param categoryName The category name to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Set the category description.
     *
     * @param categoryDescription The category description to set.
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    /**
     * Set the total number of services in this category.
     *
     * @param totalServices The total number of services to set.
     */
    public void setTotalServices(int totalServices) {
        this.totalServices = totalServices;
    }

}
