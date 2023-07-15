package com.group10.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.group10.Constants.IntegerConstants;
import com.group10.Enums.GetReviewsByServiceQueryColumns;
import com.group10.Enums.GetServiceDetailsQueryColumns;
import com.group10.Enums.UserTableColumns;
import com.group10.Enums.SearchServiceQueryColumns;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;

/**
 * Utility class for mapping a ResultSet to a User object.
 */
@Component
public class MapResultSetUtil {
    /**
     * Maps a ResultSet object to a User object.
     *
     * @param resultSet The ResultSet object containing the user data.
     * @return The User object with the mapped data.
     * @throws SQLException If there is an error accessing the ResultSet.
     */
    public User mapResultSetToUser_findByEmail(ResultSet resultSet) throws SQLException {
        User user = new User();
        int columnIndex = resultSet.findColumn(UserTableColumns.USER_ID.getColumnName());
        Object value = resultSet.getObject(columnIndex) != null ? resultSet.getObject(columnIndex) : IntegerConstants.userDoesntExist;
        user.setUserId(value instanceof Integer ? (Integer) value : Integer.parseInt((String) value));
        user.setLastName(resultSet.getString(UserTableColumns.LAST_NAME.getColumnName()));
        user.setFirstName(resultSet.getString(UserTableColumns.FIRST_NAME.getColumnName()));
        user.setMobile(resultSet.getString(UserTableColumns.MOBILE.getColumnName()));
        user.setVendor(resultSet.getInt(UserTableColumns.IS_VENDOR.getColumnName()));
        user.setEmail(resultSet.getString(UserTableColumns.EMAIL.getColumnName()));
        user.setStreet(resultSet.getString(UserTableColumns.STREET.getColumnName()));
        user.setCity(resultSet.getString(UserTableColumns.CITY.getColumnName()));
        user.setProvince(resultSet.getString(UserTableColumns.PROVINCE.getColumnName()));
        user.setCountry(resultSet.getString(UserTableColumns.COUNTRY.getColumnName()));
        user.setPassword(resultSet.getString(UserTableColumns.PASSWORD.getColumnName()));
        // Set other properties as needed
        return user;
    }

    /**
     * Maps a ResultSet object to a Review object.
     *
     * @param resultSet The ResultSet object containing the data to be mapped
     * @return The Review object with the mapped data
     * @throws SQLException If there is an error accessing the data from the ResultSet
     */
    public Review mapResultSetToReview(ResultSet resultSet) throws SQLException{
        Review review = new Review();
        review.setServiceId(resultSet.getInt(GetReviewsByServiceQueryColumns.SERVICE_ID.getColumnName()));
        review.setReviewerId(resultSet.getInt(GetReviewsByServiceQueryColumns.USER_ID.getColumnName()));
        review.setReviewTitle(resultSet.getString(GetReviewsByServiceQueryColumns.TITLE.getColumnName()));
        review.setReviewComment(resultSet.getString(GetReviewsByServiceQueryColumns.COMMENT_TEXT.getColumnName()));
        review.setReviewDate(resultSet.getString(GetReviewsByServiceQueryColumns.REVIEW_DATE.getColumnName()));
        review.setReviewRating(resultSet.getInt(GetReviewsByServiceQueryColumns.RATING.getColumnName()));
        review.setReviewerName(resultSet.getString(GetReviewsByServiceQueryColumns.NAME.getColumnName()));
        review.setReviewerCity(resultSet.getString(GetReviewsByServiceQueryColumns.CITY.getColumnName()));
        review.setReviewerCountry(resultSet.getString(GetReviewsByServiceQueryColumns.COUNTRY.getColumnName()));
        return review;
    }

    /**
     * Maps a ResultSet object to a Service object.
     *
     * @param resultSet The ResultSet object containing the data to be mapped
     * @return The mapped Service object
     * @throws SQLException If there is an error accessing the ResultSet data
     */
    public Service mapResultSetToService(ResultSet resultSet) throws SQLException {
        Service service = new Service();
    service.setServiceId(resultSet.getInt(SearchServiceQueryColumns.SERVICE_ID.getColumnName()));
        service.setUserId(resultSet.getInt(SearchServiceQueryColumns.USER_ID.getColumnName()));
        service.setServiceName(resultSet.getString(SearchServiceQueryColumns.SERVICE_NAME.getColumnName()));
        service.setServiceDescription(resultSet.getString(SearchServiceQueryColumns.SERVICE_DESCRIPTION.getColumnName()));
        service.setServicePrice(resultSet.getString(SearchServiceQueryColumns.SERVICE_PRICE.getColumnName()));
        service.setImages(new ArrayList<>());
        String categories = resultSet.getString(SearchServiceQueryColumns.CATEGORIES.getColumnName());
        if (categories != null){
            service.setCategoryNames(Arrays.asList(categories.split(",")));
        }
        else{
            service.setCategoryNames(new ArrayList<>());
        }
        service.setCompanyStreet(resultSet.getString(SearchServiceQueryColumns.COMPANY_STREET.getColumnName()));
        service.setCompanyCity(resultSet.getString(SearchServiceQueryColumns.COMPANY_CITY.getColumnName()));
        service.setCompanyProvince(resultSet.getString(SearchServiceQueryColumns.COMPANY_PROVINCE.getColumnName()));
        service.setCompanyCountry(resultSet.getString(SearchServiceQueryColumns.COMPANY_COUNTRY.getColumnName()));
        service.setAverageRating(resultSet.getString(SearchServiceQueryColumns.AVERAGE_RATING.getColumnName()));
        service.setTotalBookings(resultSet.getString(SearchServiceQueryColumns.TOTAL_BOOKINGS.getColumnName()));
        return service;
    }

    /**
     * Maps a ResultSet object to a private Service object.
     *
     * @param resultSet The ResultSet object containing the data to be mapped.
     * @return A Service object with the mapped data.
     * @throws SQLException If there is an error accessing the data from the ResultSet.
     */
    public Service mapResultSetToPrivateService(ResultSet resultSet) throws SQLException{
        Service service = new Service();
        service.setServiceId(resultSet.getInt(GetServiceDetailsQueryColumns.SERVICE_ID.getColumnName()));
        service.setUserId(resultSet.getInt(GetServiceDetailsQueryColumns.USER_ID.getColumnName()));
        service.setServiceName(resultSet.getString(GetServiceDetailsQueryColumns.SERVICE_NAME.getColumnName()));
        service.setServiceDescription(resultSet.getString(GetServiceDetailsQueryColumns.SERVICE_DESCRIPTION.getColumnName()));
        service.setServicePrice(resultSet.getString(GetServiceDetailsQueryColumns.SERVICE_PRICE.getColumnName()));
        service.setCompanyEmail(resultSet.getString(GetServiceDetailsQueryColumns.COMPANY_EMAIL.getColumnName()));
        return service;
    }
}
