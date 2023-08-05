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
import com.group10.Repository.Interfaces.IServiceRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Service;

/**
 * Repository class for managing services in the database.
 */
@Repository
@Slf4j
public class ServiceRepository implements IServiceRepository {
    
    @Autowired
    IDatabaseService databaseService;
    
    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Checks if a service exists in the database.
     *
     * @param serviceId The ID of the service to check.
     * @return true if the service exists, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean checkIfServiceExists(int serviceId) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement servicesCheckPreparedStatement = connection.prepareStatement(SQLQueries.checkIfServiceExistsQuery)) {

            log.debug("Checking if service with ID {} exists.", serviceId);

            servicesCheckPreparedStatement.setInt(1, serviceId);
            ResultSet servicesCheckResultSet = servicesCheckPreparedStatement.executeQuery();

            boolean serviceExists = servicesCheckResultSet.next();

            log.debug("Service with ID {} exists: {}", serviceId, serviceExists);

            return serviceExists;
        } catch (SQLException e) {
            log.error("Error while checking if service exists: {}", e.getMessage());
            throw new SQLException("Error while checking if service exists", e);
        }
    }


    /**
     * Retrieves a list of services based on the search parameter.
     *
     * @param searchParam The search parameter to filter services.
     * @return A list of services matching the search parameter.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException {
        List<Service> servicesList = new ArrayList<>();
        try (Connection connection = databaseService.connect();
             PreparedStatement searchServiceQueryPreparedStatement = connection.prepareStatement(SQLQueries.searchServiceQuery)) {

            log.debug("Fetching services based on search parameter: {}", searchParam);

            for (int i = 1; i <= 5; i++) {
                searchServiceQueryPreparedStatement.setString(i, searchParam);
            }

            ResultSet searchServiceResultSet = searchServiceQueryPreparedStatement.executeQuery();

            // Loop through the searchServiceResultSet set and create Service objects
            while (searchServiceResultSet.next()) {
                Service service = mapResultSetUtilObj.mapResultSetToService(searchServiceResultSet, true);
                servicesList.add(service);
            }

            log.debug("Found {} services based on search parameter: {}", servicesList.size(), searchParam);
        } catch (SQLException e) {
            log.error("Error while fetching services based on search parameter: {}", e.getMessage());
            throw new SQLException("Error while fetching services based on search parameter", e);
        }
        return servicesList;
    }


    /**
     * Retrieves details of a service based on the service ID.
     *
     * @param serviceId The ID of the service.
     * @return The service details.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Service getServiceDetails(int serviceId) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement getServiceDetailsPreparedStatement = connection.prepareStatement(SQLQueries.getServiceDetailsQuery)) {

            log.debug("Fetching details for service with ID: {}", serviceId);

            getServiceDetailsPreparedStatement.setInt(1, serviceId);
            ResultSet getServiceDetailsResultSet = getServiceDetailsPreparedStatement.executeQuery();

            if (getServiceDetailsResultSet.next()) {
                Service service = mapResultSetUtilObj.mapResultSetToPrivateService(getServiceDetailsResultSet);

                // Set company email separately to avoid exposing it on frontend
                service.setCompanyEmail(getServiceDetailsResultSet.getString("company_email"));

                log.debug("Details fetched for service with ID: {}", serviceId);
                return service;
            }
            return null;
        } catch (SQLException e) {
            log.error("Error while fetching details for service with ID {}: {}", serviceId, e.getMessage());
            throw new SQLException("Error while fetching service details", e);
        }
    }


    /**
     * Retrieves services associated with a specific vendor.
     *
     * @param userID The ID of the vendor user.
     * @return List of services associated with the vendor.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Service> getServicesForVendor(int userID) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement getServiceDetailsPreparedStatement = connection.prepareStatement(SQLQueries.getServiceDetailsByUser);
             PreparedStatement getImagesPreparedStatement = connection.prepareStatement(SQLQueries.getImagesForServiceID)) {

            log.debug("Fetching services for vendor with ID: {}", userID);

            ResultSet getServiceResultSet, getImagesResultSet;
            List<Service> serviceList = new ArrayList<>();
            Service service = null;

            getServiceDetailsPreparedStatement.setInt(1, userID);
            getServiceResultSet = getServiceDetailsPreparedStatement.executeQuery();

            while (getServiceResultSet.next()) {
                service = mapResultSetUtilObj.mapResultSetToService(getServiceResultSet, false);
                serviceList.add(service);
            }

            for (Service vendorService : serviceList) {
                getImagesPreparedStatement.setInt(1, vendorService.getServiceId());
                getImagesResultSet = getImagesPreparedStatement.executeQuery();

                while (getImagesResultSet.next()) {
                    byte[] imageData = getImagesResultSet.getBytes("image");
                    if (imageData != null) {
                        vendorService.getImages().add(Base64.getEncoder().encodeToString(imageData));
                    }
                }
            }

            log.debug("Services fetched for vendor with ID: {}", userID);
            return serviceList;
        } catch (SQLException e) {
            log.error("Error while fetching services for vendor with ID {}: {}", userID, e.getMessage());
            throw new SQLException("Error while fetching services for vendor", e);
        }
    }


    /**
     * Checks if a booking exists for a specific booking ID, service ID, and user ID.
     *
     * @param bookingId The ID of the booking.
     * @param serviceId The ID of the service.
     * @param userId The ID of the user.
     * @return True if the booking exists, otherwise false.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean checkIfBookingExists(int bookingId, int serviceId, int userId) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement bookingExistsCheckPreparedStatement = connection.prepareStatement(SQLQueries.checkIfBookingExistsQuery)) {

            log.debug("Checking if booking exists - Booking ID: {}, Service ID: {}, User ID: {}", bookingId, serviceId, userId);

            bookingExistsCheckPreparedStatement.setInt(1, bookingId);
            bookingExistsCheckPreparedStatement.setInt(2, serviceId);
            bookingExistsCheckPreparedStatement.setInt(3, userId);
            ResultSet bookingExistsQueryResultSet = bookingExistsCheckPreparedStatement.executeQuery();

            if (bookingExistsQueryResultSet.next()) {
                log.debug("Booking exists - Booking ID: {}, Service ID: {}, User ID: {}", bookingId, serviceId, userId);
                return true;
            }

            log.debug("Booking does not exist - Booking ID: {}, Service ID: {}, User ID: {}", bookingId, serviceId, userId);
            return false;
        } catch (SQLException e) {
            log.error("Error while checking if booking exists - Booking ID: {}, Service ID: {}, User ID: {}: {}", bookingId, serviceId, userId, e.getMessage());
            throw new SQLException("Error while checking if booking exists", e);
        }
    }


    /**
     * Inserts a new service into the database along with associated categories and images.
     *
     * @param service The Service object to be inserted.
     * @param categoryList The list of categories associated with the service.
     * @return The Service object with the inserted service ID, or null if insertion fails.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Service insertService(Service service, List<Category> categoryList) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement insertServiceStatement = connection.prepareStatement(SQLQueries.insertService, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertServiceCategoryStatement = connection.prepareStatement(SQLQueries.insertServiceCategoryAssociation);
             PreparedStatement insertServiceImagesStatement = connection.prepareStatement(SQLQueries.insertServiceImages);) {

            int serviceId = -1;
            List<Integer> categoryIDList = new ArrayList<>();
            List<String> categoryNames = List.copyOf(service.getCategoryNames());
            List<String> images = service.getImages();

            connection.setAutoCommit(false);

            // Insert service
            insertServiceStatement.setInt(1, service.getUserId());
            insertServiceStatement.setString(2, service.getServiceName());
            insertServiceStatement.setString(3, service.getServiceDescription());
            insertServiceStatement.setString(4, service.getServicePrice());
            insertServiceStatement.executeUpdate();

            ResultSet insertServiceResultSet = insertServiceStatement.getGeneratedKeys();

            if (insertServiceResultSet.next()) {
                serviceId = insertServiceResultSet.getInt(1);
            }

            if (serviceId < 0) {
                log.error("Failed to insert service into the database");
                return null;
            }

            // Associate categories with service
            for (Category category : categoryList) {
                if (categoryNames.contains(category.getCategoryName())) {
                    categoryIDList.add(category.getCategoryId());
                }
            }

            for (int categoryId : categoryIDList) {
                insertServiceCategoryStatement.setInt(1, serviceId);
                insertServiceCategoryStatement.setInt(2, categoryId);
                insertServiceCategoryStatement.executeUpdate();
            }

            // Insert service images
            for (String encodedImage : images) {
                insertServiceImagesStatement.setInt(1, serviceId);

                byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
                insertServiceImagesStatement.setBytes(2, imageBytes);

                insertServiceImagesStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            service.setServiceId(serviceId);

            log.info("Service with ID {} has been successfully inserted", serviceId);

            return service;
        } catch (SQLException e) {
            log.error("Error while inserting service: {}", e.getMessage());
            throw new SQLException("Error while inserting service", e);
        }
    }


    /**
     * Deletes a service from the database.
     *
     * @param serviceToDelete The Service object to be deleted.
     * @return True if the service is successfully deleted, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean deleteService(Service serviceToDelete) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement deleteServiceStatement = connection.prepareStatement(SQLQueries.deleteService);) {

            connection.setAutoCommit(false);

            deleteServiceStatement.setInt(1, serviceToDelete.getServiceId());
            int rowsAffected = deleteServiceStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            if (rowsAffected > 0) {
                log.info("Service with ID {} has been successfully deleted", serviceToDelete.getServiceId());
                return true;
            } else {
                log.warn("Service with ID {} does not exist and cannot be deleted", serviceToDelete.getServiceId());
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while deleting service: {}", e.getMessage());
            throw new SQLException("Error while deleting service", e);
        }
    }


    /**
     * Edits a service in the database.
     *
     * @param serviceToUpdate The updated Service object.
     * @param categoryList The list of categories to associate with the service.
     * @return The updated Service if the edit is successful, null otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Service editService(Service serviceToUpdate, List<Category> categoryList) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement deleteCategoryAssociationStatement = connection.prepareStatement(SQLQueries.deleteAllServiceCategoryAssociation);
             PreparedStatement insertCategoryAssociationStatement = connection.prepareStatement(SQLQueries.insertServiceCategoryAssociation);
             PreparedStatement updateServiceStatement = connection.prepareStatement(SQLQueries.updateService);) {

            List<Integer> categoryIDList = new ArrayList<>();
            List<String> categoryNames = List.copyOf(serviceToUpdate.getCategoryNames());

            connection.setAutoCommit(false);

            deleteCategoryAssociationStatement.setInt(1, serviceToUpdate.getServiceId());
            deleteCategoryAssociationStatement.executeUpdate();

            for (Category temp : categoryList) {
                if (categoryNames.contains(temp.getCategoryName())) {
                    categoryIDList.add(temp.getCategoryId());
                }
            }

            for (int categoryId : categoryIDList) {
                insertCategoryAssociationStatement.setInt(1, serviceToUpdate.getServiceId());
                insertCategoryAssociationStatement.setInt(2, categoryId);
                insertCategoryAssociationStatement.executeUpdate();
            }

            updateServiceStatement.setString(1, serviceToUpdate.getServiceName());
            updateServiceStatement.setString(2, serviceToUpdate.getServiceDescription());
            updateServiceStatement.setString(3, serviceToUpdate.getServicePrice());
            updateServiceStatement.setInt(4, serviceToUpdate.getServiceId());

            int rowsUpdated = updateServiceStatement.executeUpdate();

            if (rowsUpdated > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                log.info("Service with ID {} has been successfully edited", serviceToUpdate.getServiceId());
                return serviceToUpdate;
            }

            connection.rollback();
            connection.setAutoCommit(true);
            log.warn("Editing service with ID {} failed", serviceToUpdate.getServiceId());
            return null;

        } catch (SQLException e) {
            log.error("Error while editing service: {}", e.getMessage());
            throw new SQLException("Error while editing service", e);
        }
    }
}
