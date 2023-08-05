package com.group10.Repository.Interfaces;

import com.group10.Model.SignUpModel;
import com.group10.Model.User;

import java.sql.SQLException;
import java.util.List;


/**
 * An interface that extends IUserRepository and provides additional methods for managing customer-related data.
 */
public interface ICustomerRepository extends IUserRepository {
    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the specified email, or null if not found.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public User findByEmail(String email) throws SQLException;

    /**
     * Updates an existing user's information.
     *
     * @param user The user object containing updated information.
     * @return True if the update was successful, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean updateUser(User user) throws SQLException;

    /**
     * Adds a new user based on the provided SignUpModel.
     *
     * @param signUpModel The SignUpModel containing user's information.
     * @return True if the user was added successfully, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean addUser(SignUpModel signUpModel) throws SQLException;

    /**
     * Retrieves a list of users based on the provided list of user IDs.
     *
     * @param userIds The list of user IDs to retrieve.
     * @return A list of SignUpModel objects representing the retrieved users.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;
}
