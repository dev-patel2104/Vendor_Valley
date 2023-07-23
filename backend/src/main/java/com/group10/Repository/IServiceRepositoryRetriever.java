package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Review;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface IServiceRepositoryRetriever
{
    public List<Review> getReviewsForService(int serviceId) throws SQLException;
    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException;
    public Service getServiceDetails(int serviceId) throws SQLException;
    public List<Service> getServicesForVendor(int userID) throws SQLException;

}
