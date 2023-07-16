package com.group10.Util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.group10.Constants.Constants;
import com.group10.Enums.GetReviewsByServiceQueryColumns;
import com.group10.Enums.GetServiceDetailsQueryColumns;
import com.group10.Enums.UserTableColumns;
import com.group10.Enums.SearchServiceQueryColumns;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;
import com.group10.Model.VendorDashboard;

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
    public User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        int columnIndex = resultSet.findColumn(UserTableColumns.USER_ID.getColumnName());
        Object value = resultSet.getObject(columnIndex) != null ? resultSet.getObject(columnIndex) : Constants.USERDOESNTEXIST;
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
        review.setServiceId(resultSet.getInt(GetReviewsByServiceQueryColumns.BOOKING_ID.getColumnName()));
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

    public VendorDashboard mapResultSetToVendorDashboard(ResultSet resultSet) throws SQLException{
        VendorDashboard vendorDashboard = new VendorDashboard();
        int totalBookings = 0;
        int cancelledBookings = 0;
        int acceptedBookings = 0;
        int completedBookings = 0;
        int awaitingBookings = 0;
        int thisMonthBookings = 0;
        Set<Integer> userIds = new HashSet<>();
        List<Date> bookingDates = new ArrayList<>();
        // Create a dictionary with keys 1-12 (months) and values 0 (number of bookings in that month)
        Map<Integer, Integer> yearActivity = new HashMap<>();
        // Add keys 1-12 to the dictionary with values 0
        for (int i = 1; i <= 12; i++){
            yearActivity.put(i, 0);
        }
        while (resultSet.next()){
            // Get the row count for total bookings
            int customerId = resultSet.getInt("user_id");
            String bookingStatus = resultSet.getString("booking_status");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            Date bookingDate = resultSet.getDate("booking_date");

            if (bookingStatus.equals("cancelled")){
                cancelledBookings++;
            }
            else if (bookingStatus.equals("accepted")){
                // if endDate is before today, increment completedBookings
                if (endDate != null && endDate.before(new Date(System.currentTimeMillis()))){
                    completedBookings++;
                }
                acceptedBookings++;
            }
            else if (bookingStatus.equals("awaiting")){
                awaitingBookings++;
            }
            // if startDate is in this month, increment thisMonthBookings
            if (startDate != null){
                if (startDate.toLocalDate().getMonthValue() == java.time.LocalDate.now().getMonthValue()){
                    thisMonthBookings++;
                }
            }
            // Add bookingDate to a list for later user
            bookingDates.add(bookingDate);
            userIds.add(customerId);
            totalBookings++;
        }
        // For each date in bookingDates get the month, and increment the value of that month (a key in a yearActivity dictionary).
        for (Date date : bookingDates){
            if (date != null){
                int month = date.toLocalDate().getMonthValue();
                yearActivity.put(month, yearActivity.get(month) + 1);
            }
        } 
        vendorDashboard.setTotalCustomers(userIds.size());
        vendorDashboard.setTotalBookings(totalBookings);
        vendorDashboard.setCancelledBookings(cancelledBookings);
        vendorDashboard.setAwaitingBookings(awaitingBookings);
        vendorDashboard.setThisMonthBookings(thisMonthBookings);
        vendorDashboard.setAcceptedBookings(acceptedBookings);
        vendorDashboard.setCompletedBookings(completedBookings);
        vendorDashboard.setUserIds(userIds);
        vendorDashboard.setYearActivity(yearActivity);
        return vendorDashboard;
    }

}
