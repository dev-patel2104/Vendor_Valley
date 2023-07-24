package com.group10.Service.Interfaces;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Model.Category;
import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;

import java.sql.SQLException;
import java.util.List;

public interface IHomeService {
    // featured and trending are the services for customer home
    public List<Category> featuredCategories() throws SQLException;
    public List<com.group10.Model.Service> TrendingServices() throws SQLException;

    // vendor dashboard and customer info are for vendor home
    public VendorDashboard getVendorDashboardInfo(String JWTToken) throws SQLException, JWTVerificationException, NullPointerException;
    public List<SignUpModel> getCustomerInfo(List<Integer> userIds) throws SQLException;
}
