package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {
    
    @Autowired
    IDatabaseService databaseService;
    private final int numberOfFeaturedCategories = 3;
    private final int numberOfTrendingServices = 5;
    private List<Category> categoryList;
    private List<Service> serviceList;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;
    
    public List<Category> getFeaturedCategories() throws SQLException
    {
        int cnt =0;
        ResultSet rs1;
        try(Connection connection = databaseService.connect();
            Statement statement1 = connection.createStatement();)
        {
            rs1 = statement1.executeQuery(SQLQueries.getFeaturedCategoriesQuery);
            categoryList = new ArrayList<>();
            Category cat = null;
            while(rs1.next() && cnt < numberOfFeaturedCategories)
            {
                cat = mapResultSetUtilObj.mapResultSetToFeaturedCategories(rs1);
                categoryList.add(cat);
                cnt++;
            }

        }
        return categoryList;
    }


    public List<Service> getTrendingServices() throws SQLException
    {
        int cnt = 0;
        ResultSet rs;
        try(Connection connection = databaseService.connect();
            Statement statement1 = connection.createStatement();)
        {
            rs = statement1.executeQuery(SQLQueries.trendingServiceQuery);
            serviceList = new ArrayList<>();
            Service ser;

            // currently selects the trending services within 30 days period
            while(rs.next() && cnt < numberOfTrendingServices)
            {
                ser = mapResultSetUtilObj.mapResultSetToTrendingServiceQuery(rs);
                serviceList.add(ser);
                cnt++;
            }
            if(serviceList.size() < 3)
            {
                rs = statement1.executeQuery(SQLQueries.trendingServiceQueryDefault);
                {
                    while(rs.next() && cnt < numberOfTrendingServices)
                    {
                        ser = mapResultSetUtilObj.mapResultSetToTrendingServiceQuery(rs);
                        serviceList.add(ser);
                        cnt++;
                    }
                }
            }

        }
        return serviceList;
    }

    public List<Category> getCategories() throws SQLException
    {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.getCategories);)
        {
            List<Category> categoryList = new ArrayList<>();
            Category cat = null;
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                cat = mapResultSetUtilObj.mapResultSetToGetCategoriesQuery(resultSet);

                categoryList.add(cat);
            }
            return categoryList;
        }
        catch (SQLException e)
        {
            throw new SQLException("Database connection issue!");
        }
    }
}
