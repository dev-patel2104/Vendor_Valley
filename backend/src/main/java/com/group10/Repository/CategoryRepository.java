package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CategoryRepository implements ICategoryRepository {
    
    @Autowired
    IDatabaseService databaseService;
    private final int numberOfFeaturedCategories = 3;
    private final int numberOfTrendingServices = 5;
    private List<Category> categoryList;
    private List<Service> serviceList;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Retrieves a list of featured categories.
     *
     * @return A list of featured Category objects.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Category> getFeaturedCategories() throws SQLException {
        log.info("Fetching featured categories.");

        int cnt = 0;
        ResultSet rs1;
        try (Connection connection = databaseService.connect();
             Statement statement1 = connection.createStatement()) {

            rs1 = statement1.executeQuery(SQLQueries.getFeaturedCategoriesQuery);
            categoryList = new ArrayList<>();
            Category cat = null;
            while (rs1.next() && cnt < numberOfFeaturedCategories) {
                cat = mapResultSetUtilObj.mapResultSetToFeaturedCategories(rs1);
                categoryList.add(cat);
                cnt++;
            }
        } catch (SQLException e) {
            log.error("Error while fetching featured categories: {}", e.getMessage());
            throw new SQLException("Error while fetching featured categories", e);
        }

        log.info("Featured categories fetched successfully.");
        return categoryList;
    }


    /**
     * Retrieves a list of trending services.
     *
     * @return A list of trending Service objects.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Service> getTrendingServices() throws SQLException {
        log.info("Fetching trending services.");

        int selectedTrendingServiceCount = 0;
        ResultSet fetchTrendingServiceResultSet;
        try (Connection connection = databaseService.connect();
             Statement statement1 = connection.createStatement()) {

            fetchTrendingServiceResultSet = statement1.executeQuery(SQLQueries.trendingServiceQuery);
            serviceList = new ArrayList<>();
            Service vendorService;

            // Currently selects the trending services within a 30-day period
            while (fetchTrendingServiceResultSet.next() && selectedTrendingServiceCount < numberOfTrendingServices) {
                vendorService = mapResultSetUtilObj.mapResultSetToTrendingServiceQuery(fetchTrendingServiceResultSet);
                serviceList.add(vendorService);
                selectedTrendingServiceCount++;
            }
            if (serviceList.size() < 3) {
                fetchTrendingServiceResultSet = statement1.executeQuery(SQLQueries.trendingServiceQueryDefault);
                while (fetchTrendingServiceResultSet.next() && selectedTrendingServiceCount < numberOfTrendingServices) {
                    vendorService = mapResultSetUtilObj.mapResultSetToTrendingServiceQuery(fetchTrendingServiceResultSet);
                    serviceList.add(vendorService);
                    selectedTrendingServiceCount++;
                }
            }
        } catch (SQLException e) {
            log.error("Error while fetching trending services: {}", e.getMessage());
            throw new SQLException("Error while fetching trending services", e);
        }

        log.info("Trending services fetched successfully.");
        return serviceList;
    }


    /**
     * Retrieves a list of categories.
     *
     * @return A list of Category objects.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Category> getCategories() throws SQLException {
        log.info("Fetching categories.");

        try (Connection connection = databaseService.connect();
             PreparedStatement getCategoriesPreparedStatement = connection.prepareStatement(SQLQueries.getCategories)) {

            List<Category> categoryList = new ArrayList<>();
            Category categoryObj;
            ResultSet resultSet = getCategoriesPreparedStatement.executeQuery();

            while (resultSet.next()) {
                categoryObj = mapResultSetUtilObj.mapResultSetToGetCategoriesQuery(resultSet);
                categoryList.add(categoryObj);
            }

            log.info("Categories fetched successfully.");
            return categoryList;
        } catch (SQLException e) {
            log.error("Error while fetching categories: {}", e.getMessage());
            throw new SQLException("Error while fetching categories", e);
        }
    }

}
