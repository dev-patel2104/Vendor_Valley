package com.group10.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Review;
import com.group10.Repository.ServiceRepository;
import com.group10.Service.Interfaces.ICustomerSelectsVendorService;
import com.group10.Util.JWTTokenHandler;

@Service
public class CustomerSelectsVendorService implements ICustomerSelectsVendorService{

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    @Override
    public List<Review> getReviews(String serviceId) throws Exception {
        try{

            int serviceIdInt = Integer.parseInt(serviceId);
            boolean exists = checkIfServiceExists(serviceIdInt);
            if (!exists){
                return new ArrayList<>();
            }
            // Call repository to get reviews
            return serviceRepository.getReviewsForService(serviceIdInt);
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

    @Override
    public boolean writeReviews(Review review, String JWTToken) throws Exception {
        try{
            boolean result = false;
            // Decode JWT Token
            DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
            // Get user id from JWT Token
            int userId = jwt.getClaim("userId").asInt();
            // Set user id in review object
            review.setReviewerId(userId);
            // Create review dateTime
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            // Set review dateTime
            review.setReviewDate(formattedDateTime);
            // Call repository to write reviews
            result = serviceRepository.writeReviews(review);
            return result;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    
}
