package com.group10.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Category;
import com.group10.Model.User;
import com.group10.Model.VendorDashboard;
import com.group10.Repository.CategoryRepository;
import com.group10.Repository.VendorRepository;
import com.group10.Util.JWTTokenHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class HomeService
{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    public List<Category> featuredCategories() throws SQLException
    {
        return categoryRepository.getFeaturedCategories();
    }

    /* Currently retrieving the services which has the highest number of booking from the users side i.e., to say that we will only
     * consider the bookings made by the user. As long as the booking is made from the user we are not worried whether the booking
     * is accepted by the vendor or not because even if the vendor rejects the booking the service was still opted by the user as
     * their 1st preference.*/
    public List<com.group10.Model.Service> TrendingServices() throws SQLException
    {
        return categoryRepository.getTrendingServices();
    }

    public VendorDashboard getVendorDashboardInfo(String JWTToken) throws SQLException, JWTVerificationException, NullPointerException
    {
        // Get user id from JWT token
        DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(JWTToken);
        int userId = decodedJWT.getClaim("userId").asInt();
        if (userId == 0) {
            return null;
        }
        return vendorRepository.getStatistics(userId);
    }

    public List<User> getCustomerInfo(List<Integer> userIds) throws SQLException{
        
        return vendorRepository.getCustomerInfo(userIds);
    }
}
