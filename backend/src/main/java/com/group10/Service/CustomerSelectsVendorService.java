package com.group10.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group10.Model.Review;
import com.group10.Repository.ServiceRepository;

@Service
public class CustomerSelectsVendorService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Review> getReviews(String serviceId) throws Exception {
        try{

            int serviceIdInt = Integer.parseInt(serviceId);
            boolean exists = checkIfServiceExists(serviceIdInt);
            if (!exists){
                return new ArrayList<>();
            }
            // Call repository to get reviews
            return serviceRepository.getReviews(serviceIdInt);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Not a valid service id");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private boolean checkIfServiceExists(int serviceId) throws SQLException {
        boolean exists = false;
        try{
            exists = serviceRepository.checkIfServiceExists(serviceId);
        }
        catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
        return exists;
    }

}
