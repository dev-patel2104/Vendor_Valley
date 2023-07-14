package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.group10.Util.SqlQueries.SQLQuery;
import com.group10.Model.Review;
import com.group10.Service.DatabaseService;

@Repository
public class ServiceRepository {
    
    @Autowired
    DatabaseService databaseService;

    public boolean checkIfServiceExists(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
            PreparedStatement statement = connection.prepareStatement(SQLQuery.checkIfServiceExistsQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                return true;
            }
            return false;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }

    public List<Review> getReviews(int serviceId) throws SQLException{
        try(Connection connection = databaseService.connect();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.getReviewsForServiceQuery);)
        {
            statement.setInt(1, serviceId);
            ResultSet result = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (result.next()){
                Review review = new Review();
                review.setServiceId(result.getInt("service_id"));
                review.setReviewerId(result.getInt("user_id"));
                review.setReviewTitle(result.getString("title"));
                review.setReviewComment(result.getString("comment_text"));
                review.setReviewDate(result.getString("review_date"));
                review.setReviewRating(result.getInt("rating"));
                review.setReviewerName(result.getString("name"));
                review.setReviewerCity(result.getString("city"));
                review.setReviewerCountry(result.getString("country"));
                reviews.add(review);
            }
            return reviews;
        }
        catch (SQLException e){
            throw new SQLException("Database Connection Lost");
        }
    }
}
