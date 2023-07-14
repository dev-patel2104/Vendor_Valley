package com.group10.Service.Interfaces;

import java.util.List;

import com.group10.Model.Review;

public interface ICustomerSelectsVendorService {
    public List<Review> getReviews(String serviceId) throws Exception;
    public boolean writeReviews(Review review, String JWTToken) throws Exception;
}
