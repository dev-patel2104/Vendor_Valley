package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.group10.Util.SqlQueries.SQLQuery;
import com.group10.Model.Service;
import com.group10.Service.DatabaseService;

@Repository
public class SearchRepository {
    
    @Autowired
    DatabaseService databaseService;


    public List<Service> getSearchResults(String searchParam) throws SQLException{
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
                Service service = new Service();
                service.setServiceId(result.getInt("service_id"));
                service.setUserId(result.getInt("user_id"));
                service.setServiceName(result.getString("service_name"));
                service.setServiceDescription(result.getString("service_description"));
                service.setServicePrice(result.getString("service_price"));
                service.setImages(new ArrayList<>());
                String categories = result.getString("categories");
                if (categories != null){
                    service.setCategoryNames(Arrays.asList(categories.split(",")));
                }
                else{
                    service.setCategoryNames(new ArrayList<>());
                }
                service.setCompanyStreet(result.getString("company_street"));
                service.setCompanyCity(result.getString("company_city"));
                service.setCompanyProvince(result.getString("company_province"));
                service.setCompanyCountry(result.getString("company_country"));
                service.setAverageRating(result.getString("avgRating"));
                service.setTotalBookings(result.getString("totalBookings"));
                
                servicesList.add(service);
            }
            getImages(servicesList, searchParam);
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
        return servicesList;
    }


    private void getImages(List<Service> servicesList, String searchParam) throws SQLException {
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
    

}
