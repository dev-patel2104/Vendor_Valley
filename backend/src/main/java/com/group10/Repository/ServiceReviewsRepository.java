package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import com.group10.Service.Interfaces.IDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Model.Review;

/**
 * Repository class for managing services in the database.
 */
@Repository
public class ServiceReviewsRepository {

    @Autowired
    IDatabaseService databaseService;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;
    
        /**
     * Retrieves a list of reviews for a given service ID from the database.
     *
     * @param serviceId The ID of the service to retrieve reviews for.
     * @return A list of Review objects representing the reviews for the service.
     * @throws SQLException If there is an error connecting to the database or executing the query.
     */
    public List<Review> getReviewsForService(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
        PreparedStatement statement = connection.prepareStatement(SQLQueries.getReviewsForServiceQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (result.next()){
                Review review = mapResultSetUtilObj.mapResultSetToReview(result);
                reviews.add(review);
            }
            return reviews;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }
    /**
     * Writes a review to the database.
     *
     * @param review The review object containing the review details.
     * @return True if the review was successfully written to the database, false otherwise.
     * @throws SQLException If there is an error with the database connection.
     */
    public boolean writeReviews(Review review) throws SQLException {
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQueries.insertReviewQuery, Statement.RETURN_GENERATED_KEYS);)
        {
            statement.setInt(1, review.getBookingId());
            statement.setInt(2, review.getServiceId());
            statement.setInt(3, review.getReviewerId());
            statement.setString(4, review.getReviewTitle());
            statement.setString(5, review.getReviewComment());
            statement.setString(6, review.getReviewDate());
            statement.setInt(7, review.getReviewRating());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                return true;
            }
            return false;
        }
        catch(SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }
    
}
