package com.group10.Repository;

import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;
import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Util.MapResultSetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Constants.Constants;
import com.group10.Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing user data.
 */
@Repository
@Slf4j
public class CustomerRepositoryImpl implements ICustomerRepository {

    @Autowired
    IDatabaseService databaseService;


    @Autowired
    private User user;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;


    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return A User object representing the user with the given email, or null if not found.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public User findByEmail(String email) throws SQLException {
        log.info("Searching for user with email: {}", email);

        try (Connection connection = databaseService.connect();
             PreparedStatement getUsersPreparedStatement = connection.prepareStatement(SQLQueries.getUserByEmailID)) {

            getUsersPreparedStatement.setString(1, email);

            try (ResultSet resultSet = getUsersPreparedStatement.executeQuery()) {
                // User found
                if (resultSet.next()) {
                    User user = mapResultSetUtilObj.mapResultSetToUser(resultSet);
                    log.info("User with email '{}' found.", email);
                    return user;
                } else {
                    // User not found
                    log.info("User with email '{}' not found.", email);
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error("Error while searching for user with email '{}': {}", email, e.getMessage());
            throw new SQLException("Error while searching for user with email", e);
        }
    }


    /**
     * Updates a user's information.
     *
     * @param user The User object containing the updated information.
     * @return true if the user information is successfully updated, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean updateUser(User user) throws SQLException {
        log.info("Updating user with ID: {}", user.getUserId());

        try (Connection connection = databaseService.connect();
             PreparedStatement updateUserPreparedStatement = connection.prepareStatement(SQLQueries.updateUserQuery)) {

            if (user.getUserId() == Constants.USERDOESNTEXIST) {
                log.warn("User with ID {} doesn't exist, cannot update.", user.getUserId());
                return false;
            }

            updateUserPreparedStatement.setString(1, user.getFirstName());
            updateUserPreparedStatement.setString(2, user.getLastName());
            updateUserPreparedStatement.setString(3, user.getStreet());
            updateUserPreparedStatement.setString(4, user.getCity());
            updateUserPreparedStatement.setString(5, user.getProvince());
            updateUserPreparedStatement.setString(6, user.getCountry());
            updateUserPreparedStatement.setString(7, user.getEmail());
            updateUserPreparedStatement.setString(8, user.getMobile());
            updateUserPreparedStatement.setInt(9, user.getVendor());
            updateUserPreparedStatement.setString(10, user.getPassword());
            updateUserPreparedStatement.setInt(11, user.getUserId());

            int rowsUpdated = updateUserPreparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                log.info("User with ID {} successfully updated.", user.getUserId());
                return true;
            } else {
                log.info("User with ID {} not found, update unsuccessful.", user.getUserId());
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while updating user with ID {}: {}", user.getUserId(), e.getMessage());
            throw new SQLException("Error while updating user", e);
        }
    }


    /**
     * Adds a new user based on the provided sign-up model.
     *
     * @param signUpModel The SignUpModel containing user information for registration.
     * @return true if the user is successfully added, false if the email is already taken.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean addUser(SignUpModel signUpModel) throws SQLException {
        User user = signUpModel.buildUserModel();

        log.info("Adding user with email: {}", user.getEmail());

        try (Connection connection = databaseService.connect();
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQueries.addUserQuery, Statement.RETURN_GENERATED_KEYS)) {

            if (findByEmail(user.getEmail()) != null) {
                log.warn("User with email {} already exists, cannot add.", user.getEmail());
                return false;
            }

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
            ResultSet addUserQueryResultSet = addUserPreparedStatement.getGeneratedKeys();

            if (addUserQueryResultSet.next()) {
                log.info("User added successfully with email: {}", user.getEmail());
                return true;
            } else {
                log.error("Failed to add user with email: {}", user.getEmail());
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while adding user with email {}: {}", user.getEmail(), e.getMessage());
            throw new SQLException("Error while adding user", e);
        }
    }


    /**
     * Retrieves a list of SignUpModel objects based on the provided list of user IDs.
     *
     * @param userIds The list of user IDs for which to retrieve user details.
     * @return A list of SignUpModel objects representing the requested user details.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException {
        List<SignUpModel> userList = new ArrayList<>();
        int user_id = userIds.get(0);

        log.info("Getting user details for user ID: {}", user_id);

        try (Connection connection = databaseService.connect();
             PreparedStatement getCustomerPreparedStatement = connection.prepareStatement(SQLQueries.getUserByID)) {

            getCustomerPreparedStatement.setInt(1, user_id);
            ResultSet getCustomerResultSet = getCustomerPreparedStatement.executeQuery();

            while (getCustomerResultSet.next()) {
                SignUpModel user = mapResultSetUtilObj.mapResultSetToSignUpModel(getCustomerResultSet);
                userList.add(user);
            }

            log.info("Retrieved {} user(s) for user ID: {}", userList.size(), user_id);

        } catch (SQLException e) {
            log.error("Error while retrieving user details for user ID {}: {}", user_id, e.getMessage());
            throw new SQLException("Error while retrieving user details", e);
        }

        return userList;
    }


    /**
     * Retrieves a list of Booking objects associated with the specified user ID.
     *
     * @param userId The ID of the user for whom to retrieve booking details.
     * @return A list of Booking objects representing the requested booking details.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public List<Booking> getBookings(int userId) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();

        log.info("Getting bookings for user ID: {}", userId);

        try (Connection connection = databaseService.connect();
             PreparedStatement getBookingsPreparedStatement = connection.prepareStatement(SQLQueries.getCustomerBookings)) {

            ResultSet getBookingsResultSet;
            getBookingsPreparedStatement.setInt(1, userId);
            getBookingsResultSet = getBookingsPreparedStatement.executeQuery();

            while (getBookingsResultSet.next()) {
                Booking booking = mapResultSetUtilObj.mapResultSetToCustomerBookings(getBookingsResultSet);
                if (booking != null) {
                    bookingList.add(booking);
                }
            }

            log.info("Retrieved {} booking(s) for user ID: {}", bookingList.size(), userId);

        } catch (SQLException e) {
            log.error("Error while retrieving bookings for user ID {}: {}", userId, e.getMessage());
            throw new SQLException("Error while retrieving bookings", e);
        }

        return bookingList;
    }
}