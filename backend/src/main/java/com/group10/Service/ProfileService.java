package com.group10.Service;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Repository.Interfaces.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public abstract class ProfileService
{
    @Autowired
    protected ICustomerRepository CustomerRepositoryImpl;
    @Autowired
    protected IVendorRepository VendorRepositoryImpl;

    /**
     * Retrieves the profile information for the specified user ID.
     *
     * @param user_id The ID of the user for which to retrieve the profile information.
     * @return The SignUpModel containing the profile information.
     * @throws SQLException If an error occurs while retrieving the profile information.
     * @throws UserDoesntExistException If the specified user doesn't exist.
     */
    public SignUpModel getProfile(int user_id) throws SQLException, UserDoesntExistException {
        if (user_id < 0) {
            throw new UserDoesntExistException("Invalid user ID");
        }

        try {
            List<Integer> userIds = List.of(user_id);
            List<SignUpModel> users = CustomerRepositoryImpl.getUsers(userIds);

            if (users == null || users.isEmpty()) {
                throw new UserDoesntExistException("User not found");
            }

            SignUpModel user = users.get(0);
            log.info("Retrieved profile information for user ID: {}", user_id);
            return user;
        } catch (SQLException e) {
            log.error("Failed to retrieve profile information: {}", e.getMessage());
            throw new SQLException("Failed to retrieve profile information: " + e.getMessage());
        }
    }


    /**
     * Edits the profile information for the specified user.
     *
     * @param newInfo The updated profile information.
     * @return True if the profile information was successfully updated, false otherwise.
     * @throws SQLException If an error occurs while updating the profile information.
     * @throws NoInformationFoundException If the provided input is missing or incomplete.
     */
    public boolean editProfile(SignUpModel newInfo) throws SQLException, NoInformationFoundException {
        if (newInfo == null) {
            throw new NoInformationFoundException("Requested input is missing");
        }

        try {
            if (newInfo.getUserId() < 0) {
                throw new NoInformationFoundException("The userId for the user to be updated is not available");
            }

            User user = newInfo.buildUserModel();
            user.setUserId(newInfo.getUserId());

            boolean isUpdated = CustomerRepositoryImpl.updateUser(user);
            if (isUpdated) {
                log.info("Profile information updated for user ID: {}", user.getUserId());
            } else {
                log.warn("Profile information not updated for user ID: {}", user.getUserId());
            }

            return isUpdated;
        } catch (SQLException e) {
            log.error("Failed to update profile information: {}", e.getMessage());
            throw new SQLException("Failed to update profile information: " + e.getMessage());
        }
    }

    /**
     * Retrieves the booking information for the specified user.
     *
     * @param userId The ID of the user to retrieve booking information for.
     * @return A list of Booking objects representing the user's bookings.
     * @throws UserDoesntExistException If the user with the given ID doesn't exist.
     * @throws SQLException If an error occurs while retrieving the booking information.
     */
    public abstract List<Booking> getBookings(int userId) throws UserDoesntExistException, SQLException;
}
