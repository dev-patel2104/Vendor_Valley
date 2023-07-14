package com.group10.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;

@Component
public class MapResultSetUtil {
    public User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setLastName(resultSet.getString("last_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setMobile(resultSet.getString("mobile"));
        user.setVendor(resultSet.getInt("is_vendor"));
        user.setEmail(resultSet.getString("email"));
        user.setStreet(resultSet.getString("street"));
        user.setCity(resultSet.getString("city"));
        user.setProvince(resultSet.getString("province"));
        user.setCountry(resultSet.getString("country"));
        user.setPassword(resultSet.getString("password"));
        // Set other properties as needed
        return user;
    }

    public Review mapResultSetToReview(ResultSet resultSet) throws SQLException{
        Review review = new Review();
        review.setServiceId(resultSet.getInt("service_id"));
        review.setReviewerId(resultSet.getInt("user_id"));
        review.setReviewTitle(resultSet.getString("title"));
        review.setReviewComment(resultSet.getString("comment_text"));
        review.setReviewDate(resultSet.getString("review_date"));
        review.setReviewRating(resultSet.getInt("rating"));
        review.setReviewerName(resultSet.getString("name"));
        review.setReviewerCity(resultSet.getString("city"));
        review.setReviewerCountry(resultSet.getString("country"));
        return review;
    }

    public Service mapResultSetToService(ResultSet resultSet) throws SQLException {
        Service service = new Service();
        service.setServiceId(resultSet.getInt("service_id"));
        service.setUserId(resultSet.getInt("user_id"));
        service.setServiceName(resultSet.getString("service_name"));
        service.setServiceDescription(resultSet.getString("service_description"));
        service.setServicePrice(resultSet.getString("service_price"));
        service.setImages(new ArrayList<>());
        String categories = resultSet.getString("categories");
        if (categories != null){
            service.setCategoryNames(Arrays.asList(categories.split(",")));
        }
        else{
            service.setCategoryNames(new ArrayList<>());
        }
        service.setCompanyStreet(resultSet.getString("company_street"));
        service.setCompanyCity(resultSet.getString("company_city"));
        service.setCompanyProvince(resultSet.getString("company_province"));
        service.setCompanyCountry(resultSet.getString("company_country"));
        service.setAverageRating(resultSet.getString("avgRating"));
        service.setTotalBookings(resultSet.getString("totalBookings"));
        return service;
    }
}
