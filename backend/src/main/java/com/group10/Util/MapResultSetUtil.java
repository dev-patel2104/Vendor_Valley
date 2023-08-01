package com.group10.Util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.group10.Enums.*;
import com.group10.Model.*;
import org.springframework.stereotype.Component;

import com.group10.Constants.Constants;

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
    public Service mapResultSetToService(ResultSet resultSet, boolean getCompanyInfo) throws SQLException {
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
        if(getCompanyInfo == false)
        {
            return service;
        }
        service.setCompanyStreet(resultSet.getString(SearchServiceQueryColumns.COMPANY_STREET.getColumnName()));
        service.setCompanyCity(resultSet.getString(SearchServiceQueryColumns.COMPANY_CITY.getColumnName()));
        service.setCompanyProvince(resultSet.getString(SearchServiceQueryColumns.COMPANY_PROVINCE.getColumnName()));
        service.setCompanyCountry(resultSet.getString(SearchServiceQueryColumns.COMPANY_COUNTRY.getColumnName()));
        String ratingString =  resultSet.getString(SearchServiceQueryColumns.AVERAGE_RATING.getColumnName());
        if (ratingString!=null && ratingString.contains(".")){
            Double rating =Double.parseDouble(resultSet.getString(SearchServiceQueryColumns.AVERAGE_RATING.getColumnName()));
            service.setAverageRating(String.format("%.1f", rating));
        }
        else{
            service.setAverageRating(resultSet.getString(SearchServiceQueryColumns.AVERAGE_RATING.getColumnName()));
        }
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

    /**
     * Maps a ResultSet to a Booking object.
     *
     * @param resultSet the ResultSet containing the booking details
     * @return the mapped Booking object
     * @throws SQLException if a database access error occurs or if the ResultSet is not valid
     */
    public Booking mapResultSetToVendorsBooking(ResultSet resultSet) throws SQLException
    {
        Booking booking = new Booking();
        User user;
        booking = new Booking();
        booking.setServiceName(resultSet.getString(GetBookingDetailsQueryColumns.SERVICE_NAME.getColumnName()));
        booking.setBookingId(resultSet.getInt(GetBookingDetailsQueryColumns.BOOKING_ID.getColumnName()));
        booking.setBookingDate(resultSet.getString(GetBookingDetailsQueryColumns.BOOKING_DATE.getColumnName()));
        booking.setStartDate(resultSet.getString(GetBookingDetailsQueryColumns.START_DATE.getColumnName()));
        booking.setEndDate(resultSet.getString(GetBookingDetailsQueryColumns.END_DATE.getColumnName()));
        booking.setBookingStatus(resultSet.getString(GetBookingDetailsQueryColumns.BOOKING_STATUS.getColumnName()));
        booking.setServiceId(resultSet.getInt(GetBookingDetailsQueryColumns.SERVICE_ID.getColumnName()));

        user = mapResultSetToUser(resultSet);
        if(user.getUserId() == Constants.USERDOESNTEXIST)
        {
            return null;
        }

        booking.setUser(user);

        return booking;
    }

    /**
     * Maps a ResultSet to a VendorDashboard object.
     *
     * @param resultSet the ResultSet containing the vendor dashboard details
     * @return the mapped VendorDashboard object
     * @throws SQLException if a database access error occurs or if the ResultSet is not valid
     */
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
            int customerId = resultSet.getInt(VendorDashboardInfoQuery.USER_ID.getColumnName());
            String bookingStatus = resultSet.getString(VendorDashboardInfoQuery.BOOKING_STATUS.getColumnName());
            Date startDate = resultSet.getDate(VendorDashboardInfoQuery.START_DATE.getColumnName());
            Date endDate = resultSet.getDate(VendorDashboardInfoQuery.END_DATE.getColumnName());
            Date bookingDate = resultSet.getDate(VendorDashboardInfoQuery.BOOKING_DATE.getColumnName());

            if (bookingStatus.equals(BookingStatus.DECLINED.getBookingStatus())){
                cancelledBookings++;
            }
            else if (bookingStatus.equals(BookingStatus.ACCEPTED.getBookingStatus())){
                // if endDate is before today, increment completedBookings
                if (endDate != null && endDate.before(new Date(System.currentTimeMillis()))){
                    completedBookings++;
                }
                acceptedBookings++;
            }
            else if (bookingStatus.equals(BookingStatus.AWAITING.getBookingStatus())){
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

    public SignUpModel mapResultSetToSignUpModel(ResultSet rs) throws SQLException {
        int isVendor;
        try{
            isVendor = rs.getInt(GetUserByIdQueryColumns.IS_VENDOR.getColumnName());
        }
        catch (SQLException e){
            // do nothing
            isVendor = 0;
        }
        return SignUpModel.builder().userId(rs.getInt(GetUserByIdQueryColumns.USER_ID.getColumnName())).
                firstName((String) getValueOrNull(rs, GetUserByIdQueryColumns.FIRST_NAME.getColumnName())).
                lastName((String) getValueOrNull(rs, GetUserByIdQueryColumns.LAST_NAME.getColumnName())).
                street((String) getValueOrNull(rs, GetUserByIdQueryColumns.STREET.getColumnName())).
                city((String) getValueOrNull(rs, GetUserByIdQueryColumns.CITY.getColumnName())).
                province((String) getValueOrNull(rs, GetUserByIdQueryColumns.PROVINCE.getColumnName())).
                country((String) getValueOrNull(rs, GetUserByIdQueryColumns.COUNTRY.getColumnName())).
                email((String) getValueOrNull(rs, GetUserByIdQueryColumns.EMAIL.getColumnName())).
                mobile((String) getValueOrNull(rs, GetUserByIdQueryColumns.MOBILE.getColumnName())).
                isVendor(isVendor).
                password((String) getValueOrNull(rs, GetUserByIdQueryColumns.PASSWORD.getColumnName())).
                userRole((String) getValueOrNull(rs, GetUserByIdQueryColumns.USER_ROLE.getColumnName())).
                companyName((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_NAME.getColumnName())).
                companyEmail((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_EMAIL.getColumnName())).
                companyRegistrationID((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_REGISTRATION_NUMBER.getColumnName())).
                companyMobile((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_MOBILE.getColumnName())).
                companyStreet((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_STREET.getColumnName())).
                companyCity((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_CITY.getColumnName())).
                companyProvince((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_PROVINCE.getColumnName())).
                companyCountry((String) getValueOrNull(rs, GetUserByIdQueryColumns.COMPANY_COUNTRY.getColumnName())).
                build();
    }

    private Object getValueOrNull(ResultSet rs, String columnName) throws SQLException {
        int columnIndex = 0;
        try{

            columnIndex = rs.findColumn(columnName);
        }
        catch (SQLException e){
            return null;
        }
        Object value = rs.getObject(columnIndex) != null ? rs.getObject(columnIndex) : null;
        if (value instanceof Integer){
            value = Integer.parseInt((String) value);
        }
        if (value instanceof Double){
            value = Double.parseDouble((String) value);
        }
        return value;
    }
    
    public Booking mapResultSetToCustomerBookings(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setServiceName(rs.getString(GetBookingDetailsQueryColumns.SERVICE_NAME.getColumnName()));
        booking.setBookingId(rs.getInt(GetBookingDetailsQueryColumns.BOOKING_ID.getColumnName()));
        booking.setBookingDate(rs.getString(GetBookingDetailsQueryColumns.BOOKING_DATE.getColumnName()));
        booking.setStartDate(rs.getString(GetBookingDetailsQueryColumns.START_DATE.getColumnName()));
        booking.setEndDate(rs.getString(GetBookingDetailsQueryColumns.END_DATE.getColumnName()));
        booking.setBookingStatus(rs.getString(GetBookingDetailsQueryColumns.BOOKING_STATUS.getColumnName()));
        return booking;
    }

    public Category mapResultSetToFeaturedCategories(ResultSet rs1) throws SQLException {
        Category cat = new Category();
        cat.setTotalServices(rs1.getInt(FeaturedCategories.TOTAL_SERVICES.getColumnName()));
        cat.setCategoryId(rs1.getInt(FeaturedCategories.CATEGORY_ID.getColumnName()));
        cat.setCategoryName(rs1.getString(FeaturedCategories.CATEGORY_NAME.getColumnName()));
        cat.setCategoryDescription(rs1.getString(FeaturedCategories.CATEGORY_DESCRIPTION.getColumnName()));
        byte[] imageData = rs1.getBytes(FeaturedCategories.CATEGORY_IMAGE.getColumnName());
        if(imageData != null)
        {
            cat.setBase64Image(Base64.getEncoder().encodeToString(imageData));
        }
        else
        {
            cat.setBase64Image("");
        }
        return cat;
    }

    public Service mapResultSetToTrendingServiceQuery(ResultSet rs) throws SQLException {
        int totalBookings;
        try{
            totalBookings = rs.getInt(TrendingServiceQuery.TOTAL_BOOKINGS_FOR_SERVICE.getColumnName());
        }
        catch(SQLException e){
            totalBookings = 0;
        }
        Service ser;
        ser = new Service();
        ser.setServiceId(rs.getInt(TrendingServiceQuery.SERVICE_ID.getColumnName()));
        ser.setTotalBookingsForService(totalBookings);
        ser.setServiceName(rs.getString(TrendingServiceQuery.SERVICE_NAME.getColumnName()));
        ser.setServiceDescription(rs.getString(TrendingServiceQuery.SERVICE_DESCRIPTION.getColumnName()));
        ser.setServicePrice(rs.getString(TrendingServiceQuery.SERVICE_PRICE.getColumnName()));
        byte[] imageData = rs.getBytes(TrendingServiceQuery.SERVICE_IMAGE.getColumnName());
        if(imageData != null)
        {
            ser.setImages(new ArrayList<>());
            ser.getImages().add(Base64.getEncoder().encodeToString(imageData));
        }
        return ser;
    }

    public Category mapResultSetToGetCategoriesQuery(ResultSet resultSet) throws SQLException {
        Category cat;
        cat = new Category();
        cat.setCategoryId(resultSet.getInt(GetCategoriesQuery.CATEGORY_ID.getColumnName()));
        cat.setCategoryName(resultSet.getString(GetCategoriesQuery.CATEGORY_NAME.getColumnName()));
        cat.setCategoryDescription(resultSet.getString(GetCategoriesQuery.CATEGORY_DESCRIPTION.getColumnName()));
        return cat;
    }

    public Booking mapResultSetToHasBookingEnded(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        User user = new User();
        user.setEmail(resultSet.getString(UserTableColumns.EMAIL.getColumnName()));
        user.setFirstName(resultSet.getString(UserTableColumns.FIRST_NAME.getColumnName()));
        user.setLastName(resultSet.getString(UserTableColumns.LAST_NAME.getColumnName()));
        booking.setEndDate(resultSet.getString(GetBookingDetailsQueryColumns.END_DATE.getColumnName()));
        booking.setBookingId(resultSet.getInt(GetBookingDetailsQueryColumns.BOOKING_ID.getColumnName()));
        booking.setUser(user);
        return booking;
    }
}
