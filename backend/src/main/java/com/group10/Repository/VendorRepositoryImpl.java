package com.group10.Repository;

import com.group10.Model.*;
import com.group10.Repository.Interfaces.IVendorRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository class for managing vendor data in the database.
 */
@Repository
@Slf4j
public class VendorRepositoryImpl implements IVendorRepository {

    @Autowired
    IDatabaseService databaseService;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;


    /**
     * Adds a new user and associated vendor to the database.
     *
     * @param signUpModel The SignUpModel containing user and vendor details to be added.
     * @return True if the user and vendor were successfully added, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    @Override
    public boolean addUser(SignUpModel signUpModel) throws SQLException {
        User user = signUpModel.buildUserModel();
        Vendor vendorModel = signUpModel.buildVendorModel();
        try (Connection connection = databaseService.connect();) {
            try (PreparedStatement sqlPreparedStatement = connection.prepareStatement(SQLQueries.insertVendorQuery);
                 PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQueries.addUserQuery, Statement.RETURN_GENERATED_KEYS);) {

                int userId = 0;
                connection.setAutoCommit(false);

                addUserPreparedStatement.setString(1, user.getFirstName());
                addUserPreparedStatement.setString(2, user.getLastName());
                addUserPreparedStatement.setString(3, user.getStreet());
                addUserPreparedStatement.setString(4, user.getCity());
                addUserPreparedStatement.setString(5, user.getProvince());
                addUserPreparedStatement.setString(6, user.getCountry());
                addUserPreparedStatement.setString(7, user.getEmail());
                addUserPreparedStatement.setString(8, user.getMobile());
                addUserPreparedStatement.setInt(9, user.getVendor());
                addUserPreparedStatement.setString(10, user.getPassword());
                addUserPreparedStatement.executeUpdate();

                ResultSet addUserResultSet = addUserPreparedStatement.getGeneratedKeys();

                if (addUserResultSet.next()) {
                    userId = addUserResultSet.getInt(1);
                }

                sqlPreparedStatement.setInt(1, userId);
                sqlPreparedStatement.setString(2, vendorModel.getUserRole());
                sqlPreparedStatement.setString(3, vendorModel.getCompanyCity());
                sqlPreparedStatement.setString(4, vendorModel.getCompanyCountry());
                sqlPreparedStatement.setString(5, vendorModel.getCompanyEmail());
                sqlPreparedStatement.setString(6, vendorModel.getCompanyMobile());
                sqlPreparedStatement.setString(7, vendorModel.getCompanyName());
                sqlPreparedStatement.setString(8, vendorModel.getCompanyProvince());
                sqlPreparedStatement.setString(9, vendorModel.getCompanyRegistrationID());
                sqlPreparedStatement.setString(10, vendorModel.getCompanyStreet());
                sqlPreparedStatement.executeUpdate();

                connection.commit();
                connection.setAutoCommit(true);

                log.info("User and associated vendor added successfully");
                return true;

            } catch (SQLException e) {
                connection.rollback();
                log.error("Error while adding user and vendor to the database: {}", e.getMessage());
                throw new SQLException("Error while adding user and vendor", e);
            }
        } catch (SQLException e) {
            log.error("Error while connecting to the database: {}", e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieves statistics for a vendor's dashboard.
     *
     * @param vendorId The ID of the vendor for whom to retrieve statistics.
     * @return A VendorDashboard object containing the retrieved statistics.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public VendorDashboard getStatistics(int vendorId) throws SQLException {
        VendorDashboard vendorDashboard = null;
        try (Connection connect = databaseService.connect();
             PreparedStatement vendorDashboardInfoPreparedStatement = connect.prepareStatement(SQLQueries.vendorDashboardInfoQuery);) {

            vendorDashboardInfoPreparedStatement.setInt(1, vendorId);
            ResultSet resultSet = vendorDashboardInfoPreparedStatement.executeQuery();

            if (resultSet.next()) {
                vendorDashboard = mapResultSetUtilObj.mapResultSetToVendorDashboard(resultSet);
            }

            log.info("Statistics retrieved for vendor with ID {}", vendorId);
        } catch (SQLException e) {
            log.error("Error while retrieving statistics for vendor with ID {}: {}", vendorId, e.getMessage());
            throw new SQLException("Error while retrieving statistics", e);
        }

        return vendorDashboard;
    }


    /**
     * Retrieves a list of SignUpModel objects corresponding to the given user IDs.
     *
     * @param userIds A list of user IDs for whom to retrieve SignUpModel objects.
     * @return A list of SignUpModel objects.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException {
        List<SignUpModel> users = new ArrayList<>();
        StringBuilder getUsersQueryBuilder = new StringBuilder("SELECT user_id, first_name, last_name, email, city, country FROM users WHERE user_id IN (");

        for (int userIDIndex = 0; userIDIndex < userIds.size(); userIDIndex++) {
            getUsersQueryBuilder.append("?");
            if (userIDIndex < userIds.size() - 1) {
                getUsersQueryBuilder.append(", ");
            }
        }

        getUsersQueryBuilder.append(");");
        String getUserQuery = getUsersQueryBuilder.toString();

        try (Connection connection = databaseService.connect();
             PreparedStatement getUsersPreparedStatement = connection.prepareStatement(getUserQuery)) {
            for (int i = 0; i < userIds.size(); i++) {
                getUsersPreparedStatement.setInt(i + 1, userIds.get(i));
            }

            try (ResultSet getUsersResultSet = getUsersPreparedStatement.executeQuery()) {
                while (getUsersResultSet.next()) {
                    SignUpModel signUpModel = mapResultSetUtilObj.mapResultSetToSignUpModel(getUsersResultSet);
                    users.add(signUpModel);
                }
            }

            log.info("Retrieved SignUpModels for {} users", userIds.size());
            return users;

        } catch (SQLException e) {
            log.error("Error while retrieving SignUpModels for users: {}", e.getMessage());
            throw new SQLException("Database connection lost!", e);
        }
    }


    /**
     * Retrieves a list of Booking objects associated with a specific vendor.
     *
     * @param userId The ID of the vendor for whom to retrieve bookings.
     * @return A list of Booking objects associated with the vendor.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Booking> getBookings(int userId) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();

        try (Connection connection = databaseService.connect();
             PreparedStatement getVendorBookingPreparedStatement = connection.prepareStatement(SQLQueries.getVendorBookings)) {

            getVendorBookingPreparedStatement.setInt(1, userId);
            ResultSet getVendorBookingResultSet = getVendorBookingPreparedStatement.executeQuery();
            Booking booking = null;

            while (getVendorBookingResultSet.next()) {
                booking = mapResultSetUtilObj.mapResultSetToVendorsBooking(getVendorBookingResultSet);

                if (booking == null) {
                    log.warn("There is a booking in the database with no customer associated with it");
                    continue;
                }

                bookingList.add(booking);
            }

            log.info("Retrieved {} bookings for vendor with ID {}", bookingList.size(), userId);
        } catch (SQLException e) {
            log.error("Error while retrieving bookings for vendor with ID {}: {}", userId, e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return bookingList;
    }


    /**
     * Updates the company details of a vendor in the database.
     *
     * @param updatedDetails The SignUpModel object containing the updated company details.
     * @return The updated SignUpModel object if the update was successful, null if not found.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException {
        Vendor vendor = updatedDetails.buildVendorModel();
        vendor.setUserId(updatedDetails.getUserId());

        try (Connection connect = databaseService.connect();
             PreparedStatement updateCompanyDetailsPreparedStatement = connect.prepareStatement(SQLQueries.updateCompanyDetailsQuery);) {

            updateCompanyDetailsPreparedStatement.setString(1, vendor.getUserRole());
            updateCompanyDetailsPreparedStatement.setString(2, vendor.getCompanyName());
            updateCompanyDetailsPreparedStatement.setString(3, vendor.getCompanyEmail());
            updateCompanyDetailsPreparedStatement.setString(4, vendor.getCompanyRegistrationID());
            updateCompanyDetailsPreparedStatement.setString(5, vendor.getCompanyMobile());
            updateCompanyDetailsPreparedStatement.setString(6, vendor.getCompanyStreet());
            updateCompanyDetailsPreparedStatement.setString(7, vendor.getCompanyCity());
            updateCompanyDetailsPreparedStatement.setString(8, vendor.getCompanyProvince());
            updateCompanyDetailsPreparedStatement.setString(9, vendor.getCompanyCountry());
            updateCompanyDetailsPreparedStatement.setInt(10, vendor.getUserId());

            int rowsUpdated = updateCompanyDetailsPreparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                log.info("Company details updated for user with ID {}", vendor.getUserId());
                return updatedDetails;
            } else {
                log.warn("No company details updated for user with ID {}", vendor.getUserId());
                return null;
            }
        } catch (SQLException e) {
            log.error("Error while updating company details for user with ID {}: {}", vendor.getUserId(), e.getMessage());
            throw new SQLException("Database Issue");
        }
    }
}
