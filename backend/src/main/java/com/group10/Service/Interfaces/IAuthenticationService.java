package com.group10.Service.Interfaces;

import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;

import java.sql.SQLException;


/**
 * Interface for user authentication-related functionality.
 */
public interface IAuthenticationService {

    /**
     * Registers a new user.
     *
     * @param signUpModel The SignUpModel object containing user registration details.
     * @throws UserAlreadyPresentException   If the user already exists in the system.
     * @throws SQLException                 If an error occurs while interacting with the database.
     * @throws VendorDetailsAbsentForUserException If vendor details are absent for the user trying to register as a vendor.
     */
    boolean SignIn(SignUpModel signUpModel) throws UserAlreadyPresentException, SQLException, VendorDetailsAbsentForUserException;

    /**
     * Logs in a user.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The User object representing the logged-in user.
     * @throws UserDoesntExistException If the user does not exist in the system.
     * @throws InvalidPasswordException If the provided password is invalid.
     * @throws SQLException            If an error occurs while interacting with the database.
     */
    User login(String email, String password) throws UserDoesntExistException, InvalidPasswordException, SQLException;
}
