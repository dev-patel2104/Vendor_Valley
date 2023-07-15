package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQuery;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Service.DatabaseService;

@Repository
public class ServiceRepository {
    
    @Autowired
    DatabaseService databaseService;
    
    private MapResultSetUtil UserUtilObj = new MapResultSetUtil();

    public boolean checkIfServiceExists(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.checkIfServiceExistsQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                return true;
            }
            return false;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    public List<Review> getReviewsForService(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.getReviewsForServiceQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (result.next()){
                Review review = UserUtilObj.mapResultSetToReview(result);
                reviews.add(review);
            }
            return reviews;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException{
        List<Service> servicesList = new ArrayList<>();
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.searchServiceQuery);)
        {
            statement.setString(1, searchParam);
            statement.setString(2, searchParam);
            statement.setString(3, searchParam);
            statement.setString(4, searchParam);
            statement.setString(5, searchParam);
            ResultSet result = statement.executeQuery();
            // Loop through the result set and create Service objects
            while (result.next()){
                Service service = UserUtilObj.mapResultSetToService(result);
                servicesList.add(service);
            }
            getImagesForService(servicesList, searchParam);
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
        return servicesList;
    }

    private void getImagesForService(List<Service> servicesList, String searchParam) throws SQLException {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.getImagesForService);)
        {
            statement.setString(1, searchParam);
            statement.setString(2, searchParam);
            statement.setString(3, searchParam);
            statement.setString(4, searchParam);
            statement.setString(5, searchParam);
            ResultSet result = statement.executeQuery(); 
            while (result.next()){
                for (Service service : servicesList){
                    if (service.getServiceId() == result.getInt("service_id")){
                        service.getImages().add(result.getString("image"));
                    }
                }
            }
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    public boolean writeReviews(Review review) throws SQLException {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.insertReviewQuery, Statement.RETURN_GENERATED_KEYS);)
        {
            statement.setInt(1, review.getServiceId());
            statement.setInt(2, review.getReviewerId());
            statement.setString(3, review.getReviewTitle());
            statement.setString(4, review.getReviewComment());
            statement.setString(5, review.getReviewDate());
            statement.setInt(6, review.getReviewRating());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                return true;
            }
            return false;
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }
    
    public Service getServiceDetails(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.getServiceDetailsQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                Service service = UserUtilObj.mapResultSetToPrivateService(result);
                // Set company email separately to avoid exposing it on frontend. 
                // (The reason being mapResultSetToService method is also being 
                // used by othermethods which send Service objects to the frontend)
                service.setCompanyEmail(result.getString("company_email"));
                return service;
            }
            return null;
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

}
