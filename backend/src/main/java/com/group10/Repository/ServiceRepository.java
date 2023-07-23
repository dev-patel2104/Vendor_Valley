package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.sql.ResultSet;

import com.group10.Model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Service.DatabaseService;

/**
 * Repository class for managing services in the database.
 */
@Repository
public class ServiceRepository {
    
    @Autowired
    DatabaseService databaseService;
    
    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Checks if a service with the given service ID exists in the database.
     *
     * @param serviceId The ID of the service to check
     * @return true if the service exists, false otherwise
     * @throws SQLException if there is an error with the database connection
     */
    public boolean checkIfServiceExists(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.checkIfServiceExistsQuery);)
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

    /**
     * Retrieves a list of reviews for a given service ID from the database.
     *
     * @param serviceId The ID of the service to retrieve reviews for.
     * @return A list of Review objects representing the reviews for the service.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    public List<Review> getReviewsForService(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
        PreparedStatement statement = connection.prepareStatement(SQLQueries.getReviewsForServiceQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (result.next()){
                Review review = mapResultSetUtilObj.mapResultSetToReview(result);
                reviews.add(review);
            }
            return reviews;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    /**
     * Retrieves a list of services based on a search parameter.
     *
     * @param searchParam The search parameter to match against service names and descriptions.
     * @return A list of services that match the search parameter.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException{
        List<Service> servicesList = new ArrayList<>();
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.searchServiceQuery);)
        {
            statement.setString(1, searchParam);
            statement.setString(2, searchParam);
            statement.setString(3, searchParam);
            statement.setString(4, searchParam);
            statement.setString(5, searchParam);
            ResultSet result = statement.executeQuery();
            // Loop through the result set and create Service objects
            while (result.next()){
                Service service = mapResultSetUtilObj.mapResultSetToService(result, true);
                servicesList.add(service);
            }
            /**
             * Retrieves images for a given service and search parameter.
             *
             * @param servicesList A list of services to search for images.
             * @param searchParam The search parameter to use when retrieving images.
             * @return A list of images related to the given service and search parameter.
             */
            getImagesForService(servicesList, searchParam);
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
        return servicesList;
    }

    /**
     * Retrieves images for a given service based on a search parameter.
     *
     * @param servicesList The list of services to update with images.
     * @param searchParam The search parameter to match against service records.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    private void getImagesForService(List<Service> servicesList, String searchParam) throws SQLException {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.getImagesForService);)
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
                        byte[] imageData = result.getBytes("image");
                        if(imageData != null)
                        {
                            service.getImages().add(Base64.getEncoder().encodeToString(imageData));
                        }
                    }
                }
            }
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    /**
     * Writes a review to the database.
     *
     * @param review The review object containing the review details.
     * @return True if the review was successfully written to the database, false otherwise.
     * @throws SQLException If there is an error with the database connection.
     */
    public boolean writeReviews(Review review) throws SQLException {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.insertReviewQuery, Statement.RETURN_GENERATED_KEYS);)
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
    
    /**
     * Retrieves the details of a service based on the given service ID.
     *
     * @param serviceId The ID of the service to retrieve details for.
     * @return The Service object containing the details of the service, or null if no service is found.
     * @throws SQLException If there is an error with the database connection or query execution.
     */
    public Service getServiceDetails(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.getServiceDetailsQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                Service service = mapResultSetUtilObj.mapResultSetToPrivateService(result);
                /**
                * Set company email separately to avoid exposing it on frontend. 
                * (The reason being mapResultSetToService method is also being 
                * used by othermethods which send Service objects to the frontend)
                */
                service.setCompanyEmail(result.getString("company_email"));
                return service;
            }
            return null;
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    public List<Service> getServicesForVendor(int userID) throws SQLException
    {

        try(Connection connection = databaseService.connect();
            PreparedStatement statement1 = connection.prepareStatement(SQLQueries.getServiceDetailsByUser);
            PreparedStatement statement2 = connection.prepareStatement(SQLQueries.getImagesForServiceID);)
        {
            ResultSet resultSet1, resultSet2;
            List<Service> serviceList = new ArrayList<>();
            Service service = null;
            statement1.setInt(1, userID);
            resultSet1 = statement1.executeQuery();
            while(resultSet1.next())
            {
                service = mapResultSetUtilObj.mapResultSetToService(resultSet1, false);
                serviceList.add(service);
            }

            for(Service temp: serviceList)
            {
                statement2.setInt(1, temp.getServiceId());
                resultSet2 = statement2.executeQuery();
                while(resultSet2.next())
                {
                    byte[] imageData = resultSet2.getBytes("image");
                    if(imageData != null)
                    {
                        temp.getImages().add(Base64.getEncoder().encodeToString(imageData));
                    }
                }

            }
            return serviceList;
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }

    /**

     Checks if a booking with the given booking ID exists in the database.*
     @param bookingId The ID of the booking to check
     @return true if the booking exists, false otherwise
     @throws SQLException if there is an error with the database connection
     */
    public boolean checkIfBookingExists(int bookingId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.checkIfBookingExistsQuery);){
            statement.setInt(1, bookingId);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                return true;}
            return false;}
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");}
    }
    public Service insertService(Service service, List<Category> categoryList) throws SQLException
    {
        try(Connection connection = databaseService.connect();
        PreparedStatement statement1 = connection.prepareStatement(SQLQueries.insertService,Statement.RETURN_GENERATED_KEYS);
        PreparedStatement statement2 = connection.prepareStatement(SQLQueries.insertServiceCategoryAssociation);
        PreparedStatement statement3 = connection.prepareStatement(SQLQueries.insertServiceImages);)
        {
            int serviceId = -1;
            List<Integer> categoryIDList = new ArrayList<>();
            List<String> categoryNames = List.copyOf(service.getCategoryNames());
            List<String> images = service.getImages();

            connection.setAutoCommit(false);

            statement1.setInt(1,service.getUserId());
            statement1.setString(2, service.getServiceName());
            statement1.setString(3, service.getServiceDescription());
            statement1.setString(4, service.getServicePrice());
            statement1.executeUpdate();

            ResultSet rs = statement1.getGeneratedKeys();

            if (rs.next()) {
                serviceId = rs.getInt(1);
            }

            if(serviceId < 0)
            {
                return null;
            }

            for(Category temp: categoryList)
            {
                if(categoryNames.contains(temp.getCategoryName()))
                {
                    categoryIDList.add(temp.getCategoryId());
                }
            }

            for(int categoryId : categoryIDList)
            {
                statement2.setInt(1, serviceId);
                statement2.setInt(2, categoryId);
                statement2.executeUpdate();
            }

            for(String encodedImage : images)
            {
                statement3.setInt(1,serviceId);

                byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
                statement3.setBytes(2,imageBytes);

                statement3.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            service.setServiceId(serviceId);
           return service;
        }
        catch (SQLException e)
        {
            throw new SQLException("Database Issue");
        }
    }

    public boolean deleteService(Service serviceToDelete) throws SQLException
    {
        try(Connection connection = databaseService.connect();
        PreparedStatement statement1 = connection.prepareStatement(SQLQueries.deleteAllServiceCategoryAssociation);
        PreparedStatement statement2 = connection.prepareStatement(SQLQueries.deleteAllServiceImages);
        PreparedStatement statement3 = connection.prepareStatement(SQLQueries.deleteService);)
        {
            connection.setAutoCommit(false);
            statement1.setInt(1, serviceToDelete.getServiceId());
            statement1.executeUpdate();

            statement2.setInt(1, serviceToDelete.getServiceId());
            statement2.executeUpdate();

            statement3.setInt(1, serviceToDelete.getServiceId());
            statement3.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }
        catch (SQLException e)
        {
            throw new SQLException("Database Issue");
        }
    }
    public Service editService(Service serviceToUpdate, List<Category> categoryList) throws SQLException
    {
        try(Connection connection = databaseService.connect();
        PreparedStatement statement1 = connection.prepareStatement(SQLQueries.deleteAllServiceCategoryAssociation);
        PreparedStatement statement2 = connection.prepareStatement(SQLQueries.insertServiceCategoryAssociation);
        PreparedStatement statement3 = connection.prepareStatement(SQLQueries.updateService);)
        {
            List<Integer> categoryIDList = new ArrayList<>();
            List<String> categoryNames = List.copyOf(serviceToUpdate.getCategoryNames());

            connection.setAutoCommit(false);
            statement1.setInt(1, serviceToUpdate.getServiceId());
            statement1.executeUpdate();

            for(Category temp: categoryList)
            {
                if(categoryNames.contains(temp.getCategoryName()))
                {
                    categoryIDList.add(temp.getCategoryId());
                }
            }

            for(int categoryId : categoryIDList)
            {
                statement2.setInt(1, serviceToUpdate.getServiceId());
                statement2.setInt(2, categoryId);
                statement2.executeUpdate();
            }

            statement3.setString(1, serviceToUpdate.getServiceName());
            statement3.setString(2, serviceToUpdate.getServiceDescription());
            statement3.setString(3, serviceToUpdate.getServicePrice());
            statement3.setInt(4, serviceToUpdate.getServiceId());

            int rowsUpdated = statement3.executeUpdate();

            if(rowsUpdated > 0)
            {
                connection.commit();
                connection.setAutoCommit(true);
                return serviceToUpdate;
            }

            return null;

        }
        catch(SQLException e)
        {
            throw new SQLException("Database Issue");
        }
    }
    public Service editServiceImage(Service service) throws SQLException
    {
        try(Connection connection = databaseService.connect();
        PreparedStatement statement1 = connection.prepareStatement(SQLQueries.deleteAllServiceImages);
        PreparedStatement statement2 = connection.prepareStatement(SQLQueries.insertServiceImages);)
        {
            connection.setAutoCommit(false);
            statement1.setInt(1, service.getServiceId());

            for(String encodedImage : service.getImages())
            {
                statement2.setInt(1, service.getServiceId());
                byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
                statement2.setBytes(2, imageBytes);
                statement2.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
            return service;
        }
        catch (SQLException e)
        {
            throw new SQLException("Database Issue");
        }
    }
}
