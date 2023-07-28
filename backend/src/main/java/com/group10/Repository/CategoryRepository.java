package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Repository
public class CategoryRepository implements ICategoryRepository {
    
    @Autowired
    IDatabaseService databaseService;
    private final int numberOfFeaturedCategories = 3;
    private final int numberOfTrendingServices = 5;
    private List<Category> categoryList;
    private List<Service> serviceList;
    
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
                cat = new Category();
                cat.setTotalServices(rs1.getInt(1));
                cat.setCategoryId(rs1.getInt(2));
                cat.setCategoryName(rs1.getString(3));
                cat.setCategoryDescription(rs1.getString(4));
                byte[] imageData = rs1.getBytes(5);
                if(imageData != null)
                {
                    cat.setBase64Image(Base64.getEncoder().encodeToString(imageData));
                }
                else
                {
                    cat.setBase64Image("");
                }
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

            // currently selects the trending services within 30 days period will later change this to a longer period
            while(rs.next() && cnt < numberOfTrendingServices)
            {
                ser = new Service();
                ser.setServiceId(rs.getInt(1));
                ser.setTotalBookingsForService(rs.getInt(2));
                ser.setServiceName(rs.getString(3));
                ser.setServiceDescription(rs.getString(4));
                ser.setServicePrice(rs.getString(5));
                byte[] imageData = rs.getBytes(6);
                if(imageData != null)
                {
                    ser.setImages(new ArrayList<>());
                    ser.getImages().add(Base64.getEncoder().encodeToString(imageData));
                }
                serviceList.add(ser);
                cnt++;
            }
            if(serviceList.size() < 3)
            {
                rs = statement1.executeQuery(SQLQueries.trendingServiceQueryDefault);
                {
                    while(rs.next() && cnt < numberOfTrendingServices)
                    {
                        ser = new Service();
                        ser.setServiceId(rs.getInt(1));
                        ser.setServiceName(rs.getString(3));
                        ser.setServiceDescription(rs.getString(4));
                        ser.setServicePrice(rs.getString(5));
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
                cat = new Category();
                cat.setCategoryId(resultSet.getInt(1));
                cat.setCategoryName(resultSet.getString(2));
                cat.setCategoryDescription(resultSet.getString(3));

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
