package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import com.group10.Repository.Interfaces.IReviewsRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Review;

/**
 * Repository class for managing services in the database.
 */
@Repository
@Slf4j
public class ServiceReviewsRepository implements IReviewsRepository {

    @Autowired
    IDatabaseService databaseService;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Retrieves reviews for a specific service from the database.
     *
     * @param serviceId The ID of the service for which reviews are retrieved.
     * @return A list of Review objects for the given service.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Review> getReviewsForService(int serviceId) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement getReviewsPreparedStatement = connection.prepareStatement(SQLQueries.getReviewsForServiceQuery);) {

            getReviewsPreparedStatement.setInt(1, serviceId);
            ResultSet getReviewsResultSet = getReviewsPreparedStatement.executeQuery();
            List<Review> reviews = new ArrayList<>();

            while (getReviewsResultSet.next()) {
                Review review = mapResultSetUtilObj.mapResultSetToReview(getReviewsResultSet);
                reviews.add(review);
            }

            log.info("Retrieved {} reviews for service with ID {}", reviews.size(), serviceId);
            return reviews;

        } catch (SQLException e) {
            log.error("Error while retrieving reviews for service with ID {}: {}", serviceId, e.getMessage());
            throw new SQLException("Error while retrieving reviews", e);
        }
    }


    /**
     * Writes a review to the database.
     *
     * @param review The Review object containing the review details to be written.
     * @return True if the review was successfully written, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean writeReviews(Review review) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement insertReviewPreparedStatement = connection.prepareStatement(SQLQueries.insertReviewQuery, Statement.RETURN_GENERATED_KEYS);) {

            insertReviewPreparedStatement.setInt(1, review.getBookingId());
            insertReviewPreparedStatement.setInt(2, review.getServiceId());
            insertReviewPreparedStatement.setInt(3, review.getReviewerId());
            insertReviewPreparedStatement.setString(4, review.getReviewTitle());
            insertReviewPreparedStatement.setString(5, review.getReviewComment());
            insertReviewPreparedStatement.setString(6, review.getReviewDate());
            insertReviewPreparedStatement.setInt(7, review.getReviewRating());
            insertReviewPreparedStatement.executeUpdate();

            ResultSet insertReviewResultSet = insertReviewPreparedStatement.getGeneratedKeys();

            if (insertReviewResultSet.next()) {
                int reviewId = insertReviewResultSet.getInt(1);
                log.info("Review with ID {} has been successfully written", reviewId);
                return true;
            }

            log.warn("Failed to write review to the database");
            return false;

        } catch (SQLException e) {
            log.error("Error while writing review to the database: {}", e.getMessage());
            throw new SQLException("Error while writing review", e);
        }
    }
}
