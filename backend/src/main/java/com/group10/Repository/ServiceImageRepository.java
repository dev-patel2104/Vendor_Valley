package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.sql.ResultSet;

import com.group10.Service.Interfaces.IDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Service;

/**
 * Repository class for managing services in the database.
 */
@Repository
public class ServiceImageRepository {

    @Autowired
    IDatabaseService databaseService;

        /**
     * Retrieves images for a given service based on a search parameter.
     *
     * @param servicesList The list of services to update with images.
     * @param searchParam The search parameter to match against service records.
         * @return 
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    public List<Service> getImagesForService(List<Service> servicesList, String searchParam) throws SQLException {
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
            return servicesList;
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
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
