package com.group10.Service;

import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Repository.Interfaces.IVendorRepository;
import com.group10.Service.Interfaces.IAuthenticationService;
import com.group10.Util.EmailUtil;
import com.group10.Util.PasswordUtil;
import com.group10.Util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService
{
    @Autowired
    private ICustomerRepository CustomerRepositoryImpl;
    @Autowired
    private IVendorRepository VendorRepositoryImpl;
    @Autowired
    private User user;
    private final int IS_VENDOR = 1;
    private final int IS_NOT_VENDOR = 0;

    /**
     * Signs up a user with the provided sign-up model.
     *
     * @param signUpModel The sign-up model containing user information.
     * @return True if the user registration is successful, false if validation fails or registration fails.
     * @throws UserAlreadyPresentException If the user is already registered.
     * @throws SQLException If there is an error while accessing the database.
     * @throws VendorDetailsAbsentForUserException If vendor details are absent for a vendor user.
     */
    public boolean SignIn(SignUpModel signUpModel) throws UserAlreadyPresentException, VendorDetailsAbsentForUserException, SQLException {
        // Validate the sign-up model
        if (validateSignUpModel(signUpModel)) {
            return false;
        }

        try {
            if (signUpModel.getIsVendor() == IS_VENDOR) {
                // Register vendor user
                if (VendorRepositoryImpl.addUser(signUpModel)) {
                    log.info("Vendor user registered successfully: {}", signUpModel.getEmail());
                    return true;
                } else {
                    throw new VendorDetailsAbsentForUserException("User details saved to db, but vendor details are missing in the database");
                }
            } else {
                // Register customer user
                if (CustomerRepositoryImpl.addUser(signUpModel)) {
                    log.info("Customer user registered successfully: {}", signUpModel.getEmail());
                    return true;
                } else {
                    throw new UserAlreadyPresentException("User Already Exists!");
                }
            }
        } catch (SQLException e) {
            log.error("Error while signing up user: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The logged-in user.
     * @throws UserDoesntExistException If the user with the given email doesn't exist.
     * @throws InvalidPasswordException If the password entered is incorrect.
     * @throws SQLException If there is an error while accessing the database.
     */
    public User login(String email, String password) throws UserDoesntExistException, InvalidPasswordException, SQLException {
        try{
            /**
             * Retrieves a user from the user repository based on their email.
             *
             * @param email The email of the user to retrieve.
             * @return The user entity associated with the given email, or null if no user is found.
             */
            user = CustomerRepositoryImpl.findByEmail(email);
            log.info("User retrieved from the repository for email: {}", email);
        } catch (SQLException e) {
            log.error("Error while retrieving user from the repository: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }

        if (user == null) {
            log.warn("User with email {} doesn't exist.", email);
            throw new UserDoesntExistException("User Doesn't Exist!");
        }

        if (!user.getPassword().equalsIgnoreCase(password)) {
            log.warn("Invalid password provided for user with email: {}", email);
            throw new InvalidPasswordException("Wrong Password Entered!");
        }

        log.info("User logged in successfully: {}", email);
        return user;
    }


    /**
     * Validates the provided sign-up model.
     *
     * @param signUpModel The sign-up model to validate.
     * @return True if the sign-up model is invalid, false if it's valid.
     */
    private boolean validateSignUpModel(SignUpModel signUpModel) {
        if (signUpModel == null) {
            log.warn("Sign-up model is null.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getFirstName())) {
            log.warn("First name is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getLastName())) {
            log.warn("Last name is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getMobile())) {
            log.warn("Mobile is null or empty.");
            return true;
        }
        if (signUpModel.getIsVendor() != IS_NOT_VENDOR && signUpModel.getIsVendor() != IS_VENDOR) {
            log.warn("Invalid vendor value: {}", signUpModel.getIsVendor());
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getStreet())) {
            log.warn("Street is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCity())) {
            log.warn("City is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getProvince())) {
            log.warn("Province is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCountry())) {
            log.warn("Country is null or empty.");
            return true;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getEmail())) {
            log.warn("Email is null or empty.");
            return true;
        }
        if (!PasswordUtil.isValidPassword(signUpModel.getPassword())) {
            log.warn("Invalid password.");
            return true;
        }

        if (!EmailUtil.isValidEmail(signUpModel.getEmail())) {
            log.warn("Invalid email format: {}", signUpModel.getEmail());
            return true;
        }

        return false;
    }
}
