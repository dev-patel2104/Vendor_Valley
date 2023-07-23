package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryRepository
{
    public List<Category> getFeaturedCategories() throws SQLException;
    public List<Service> getTrendingServices() throws SQLException;
    public List<Category> getCategories() throws SQLException;
}
