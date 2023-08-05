package com.group10.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Category;
import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;
import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Repository.Interfaces.IVendorRepository;
import com.group10.Service.Interfaces.IHomeService;
import com.group10.Util.JWTTokenHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class HomeServiceImpl implements IHomeService
{

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IVendorRepository VendorRepositoryImpl;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    /**
     * Retrieves the featured categories for the customer's home page.
     *
     * @return A list of featured categories.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    @Override
    public List<Category> featuredCategories() throws SQLException {
        try {
            List<Category> categories = categoryRepository.getFeaturedCategories();
            log.info("Retrieved {} featured categories.", categories.size());
            return categories;
        } catch (SQLException e) {
            log.error("Failed to retrieve featured categories: {}", e.getMessage());
            throw new SQLException("Failed to retrieve featured categories: " + e.getMessage());
        }
    }

    /**
     * Retrieves the trending services for the customer's home page. Currently retrieving the services which has the highest number of booking from the users side i.e.,
     * to say that we will only consider the bookings made by the user.
     *
     * @return A list of trending services.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    @Override
    public List<com.group10.Model.Service> trendingServices() throws SQLException {
        try {
            List<com.group10.Model.Service> services = categoryRepository.getTrendingServices();
            log.info("Retrieved {} trending services.", services.size());
            return services;
        } catch (SQLException e) {
            log.error("Failed to retrieve trending services: {}", e.getMessage());
            throw new SQLException("Failed to retrieve trending services: " + e.getMessage());
        }
    }

    /**
     * Retrieves the vendor dashboard information for the vendor's home page.
     *
     * @param JWTToken The JWT token representing the authenticated vendor.
     * @return The VendorDashboard object containing the vendor's dashboard information.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     * @throws JWTVerificationException If there is an issue with JWT token verification.
     * @throws NullPointerException If the JWT token is null.
     */
    public VendorDashboard getVendorDashboardInfo(String JWTToken) throws SQLException, JWTVerificationException, NullPointerException {
        try {
            // Get user id from JWT token
            DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(JWTToken);
            int userId = decodedJWT.getClaim("userId").asInt();
            if (userId == 0) {
                return null;
            }

            VendorDashboard dashboard = VendorRepositoryImpl.getStatistics(userId);
            log.info("Retrieved dashboard statistics for vendor with userId: {}", userId);
            return dashboard;
        } catch (JWTVerificationException e) {
            log.error("JWT token verification failed: {}", e.getMessage());
            throw e;
        } catch (NullPointerException e) {
            log.error("JWTToken is null: {}", e.getMessage());
            throw e;
        } catch (SQLException e) {
            log.error("Failed to retrieve dashboard statistics: {}", e.getMessage());
            throw new SQLException("Failed to retrieve dashboard statistics: " + e.getMessage());
        }
    }

    /**
     * Retrieves customer information based on their user IDs.
     *
     * @param userIds The list of user IDs for which customer information is to be retrieved.
     * @return A list of User objects representing the customer information.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    public List<SignUpModel> getCustomerInfo(List<Integer> userIds) throws SQLException {
        try {
            List<SignUpModel> customerInfo = VendorRepositoryImpl.getUsers(userIds);
            log.info("Retrieved customer information for user IDs: {}", userIds);
            return customerInfo;
        } catch (SQLException e) {
            log.error("Failed to retrieve customer information: {}", e.getMessage());
            throw new SQLException("Failed to retrieve customer information: " + e.getMessage());
        }
    }
}
