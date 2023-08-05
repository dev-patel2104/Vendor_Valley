package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.sql.ResultSet;

import com.group10.Repository.Interfaces.IImageRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Service;

/**
 * Repository class for managing services in the database.
 */
@Repository
@Slf4j
public class ServiceImageRepository implements IImageRepository {

    @Autowired
    IDatabaseService databaseService;

    /**
     * Retrieves images for the provided list of services based on the given search parameter.
     *
     * @param servicesList The list of services to update with images.
     * @param searchParam  The search parameter to filter images.
     * @return The updated list of services with images.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Service> getImagesForService(List<Service> servicesList, String searchParam) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement getImagesForServicePreparedStatement = connection.prepareStatement(SQLQueries.getImagesForService)) {
            getImagesForServicePreparedStatement.setString(1, searchParam);
            getImagesForServicePreparedStatement.setString(2, searchParam);
            getImagesForServicePreparedStatement.setString(3, searchParam);
            getImagesForServicePreparedStatement.setString(4, searchParam);
            getImagesForServicePreparedStatement.setString(5, searchParam);
            ResultSet getImagesForServiceResultSet = getImagesForServicePreparedStatement.executeQuery();

            while (getImagesForServiceResultSet.next()) {
                for (Service service : servicesList) {
                    if (service.getServiceId() == getImagesForServiceResultSet.getInt("service_id")) {
                        byte[] imageData = getImagesForServiceResultSet.getBytes("image");
                        if (imageData != null) {
                            service.getImages().add(Base64.getEncoder().encodeToString(imageData));
                            log.debug("Added image to service ID {}: {}", service.getServiceId(), getImagesForServiceResultSet.getInt("image_id"));
                        }
                    }
                }
            }

            log.info("Retrieved images for services based on search parameter: {}", searchParam);
            return servicesList;
        } catch (SQLException e) {
            log.error("Error while retrieving images for services: {}", e.getMessage());
            throw new SQLException("Error while retrieving images for services", e);
        }
    }


    /**
     * Edits the images for the specified service in the database.
     *
     * @param service The service containing updated images.
     * @return The service with updated images.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Service editServiceImage(Service service) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement deleteStatement = connection.prepareStatement(SQLQueries.deleteAllServiceImages);
             PreparedStatement insertStatement = connection.prepareStatement(SQLQueries.insertServiceImages)) {

            log.debug("Starting the process of editing service images for service ID {}.", service.getServiceId());

            connection.setAutoCommit(false);

            deleteStatement.setInt(1, service.getServiceId());
            int deletedRows = deleteStatement.executeUpdate();
            log.debug("{} image(s) deleted for service ID {}.", deletedRows, service.getServiceId());

            insertStatement.setInt(1, service.getServiceId());

            for (String encodedImage : service.getImages()) {
                byte[] imageBytes = Base64.getDecoder().decode(encodedImage);
                insertStatement.setBytes(2, imageBytes);
                insertStatement.addBatch();
            }

            int[] insertedRows = insertStatement.executeBatch();
            log.debug("{} image(s) inserted for service ID {}.", insertedRows.length, service.getServiceId());

            connection.commit();
            connection.setAutoCommit(true);

            log.info("Images for service ID {} have been updated.", service.getServiceId());
            return service;
        } catch (SQLException e) {
            log.error("Error while editing service images: {}", e.getMessage());
            throw new SQLException("Error while editing service images", e);
        }
    }
}
