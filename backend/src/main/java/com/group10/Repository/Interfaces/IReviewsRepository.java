package com.group10.Repository.Interfaces;

import com.group10.Model.Review;

import java.sql.SQLException;
import java.util.List;


/**
 * An interface that provides methods for writing and retrieving reviews for services.
 */
public interface IReviewsRepository
{
    /**
     * Writes a review for a service.
     *
     * @param review The review object containing review details.
     * @return true if the review is successfully written, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean writeReviews(Review review) throws SQLException;

    /**
     * Retrieves reviews associated with a specific service.
     *
     * @param serviceId The ID of the service.
     * @return A list of reviews for the specified service.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Review> getReviewsForService(int serviceId) throws SQLException;
}
