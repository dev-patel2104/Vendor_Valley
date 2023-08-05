package com.group10.Repository.Interfaces;

import com.group10.Model.Category;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;


/**
 * An interface that defines methods for retrieving categories and services related to categories.
 */
public interface ICategoryRepository
{
    /**
     * Retrieves a list of featured categories.
     *
     * @return A list of featured categories.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Category> getFeaturedCategories() throws SQLException;

    /**
     * Retrieves a list of trending services.
     *
     * @return A list of trending services.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Service> getTrendingServices() throws SQLException;

    /**
     * Retrieves a list of all available categories.
     *
     * @return A list of categories.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Category> getCategories() throws SQLException;
}
