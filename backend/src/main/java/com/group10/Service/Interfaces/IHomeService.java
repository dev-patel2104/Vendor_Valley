package com.group10.Service.Interfaces;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Model.Category;
import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;

import java.sql.SQLException;
import java.util.List;


/**
 * Interface for home-related services.
 */
public interface IHomeService {

    /**
     * Retrieves a list of featured categories for the customer's home.
     *
     * @return A list of Category objects representing featured categories.
     * @throws SQLException If a database error occurs.
     */
    List<Category> featuredCategories() throws SQLException;

    /**
     * Retrieves a list of trending services for the customer's home.
     *
     * @return A list of Service objects representing trending services.
     * @throws SQLException If a database error occurs.
     */
    List<com.group10.Model.Service> trendingServices() throws SQLException;

    /**
     * Retrieves the vendor dashboard information based on a JWT token.
     *
     * @param JWTToken The JWT token representing the authenticated user.
     * @return A VendorDashboard object containing vendor dashboard information.
     * @throws SQLException          If a database error occurs.
     * @throws JWTVerificationException If there is an issue with JWT verification.
     * @throws NullPointerException    If the JWTToken is null.
     */
    VendorDashboard getVendorDashboardInfo(String JWTToken) throws SQLException, JWTVerificationException, NullPointerException;

    /**
     * Retrieves customer information for a list of user IDs.
     *
     * @param userIds A list of user IDs for which customer information is to be retrieved.
     * @return A list of SignUpModel objects representing customer information.
     * @throws SQLException If a database error occurs.
     */
    List<SignUpModel> getCustomerInfo(List<Integer> userIds) throws SQLException;
}

