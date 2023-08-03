package com.group10.Repository.Interfaces;

import com.group10.Model.Review;

import java.sql.SQLException;
import java.util.List;

public interface IReviewsRepository
{
    public boolean writeReviews(Review review) throws SQLException;
    public List<Review> getReviewsForService(int serviceId) throws SQLException;
}
