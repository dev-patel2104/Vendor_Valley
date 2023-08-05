package com.group10.Repository.Interfaces;

import java.sql.SQLException;
import java.util.List;

import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;

/**
 * An interface that provides methods for interacting with user-related data in the repository.
 */
public interface IUserRepository {
    /**
     * Adds a new user based on the provided sign-up model.
     *
     * @param signUpModel The sign-up model containing user details.
     * @return true if the user is successfully added, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean addUser(SignUpModel signUpModel) throws SQLException;

    /**
     * Retrieves a list of users based on the provided user IDs.
     *
     * @param userIds The list of user IDs to retrieve.
     * @return A list of users matching the specified user IDs.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;

    /**
     * Retrieves a list of bookings associated with a specific user.
     *
     * @param userId The ID of the user for whom to retrieve bookings.
     * @return A list of bookings associated with the specified user.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Booking> getBookings(int userId) throws SQLException;
}
