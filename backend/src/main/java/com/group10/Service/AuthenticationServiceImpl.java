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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
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

    public boolean SignIn(SignUpModel signUpModel) throws UserAlreadyPresentException, SQLException, VendorDetailsAbsentForUserException {
        if (signUpModel == null) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getFirstName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getLastName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getMobile())) {
            return false;
        }
        if(signUpModel.getIsVendor() != IS_NOT_VENDOR && signUpModel.getIsVendor() != IS_VENDOR) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getStreet())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCity())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getProvince())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getCountry())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(signUpModel.getEmail())) {
            return false;
        }
        if(!PasswordUtil.isValidPassword(signUpModel.getPassword())) {
            return false;
        }

        if(!EmailUtil.isValidEmail(signUpModel.getEmail())) {
            return false;
        }

        if(signUpModel.getIsVendor() == IS_VENDOR)
        {
            if(VendorRepositoryImpl.addUser(signUpModel))
            {
                return true;
            }
            else {
                throw new VendorDetailsAbsentForUserException("User details saved to db, but vendor details are missing in database");
            }

        }
        else
        {
            if(CustomerRepositoryImpl.addUser(signUpModel)){
                return true;
            }
            else{
                throw new UserAlreadyPresentException("User Already Exists!");
            }

        }

    }
    /**
     * Logs in a user with the given email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The logged in user.
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
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        if (user == null) {
            throw new UserDoesntExistException("User Doesn't Exists!");
        }
        if (!user.getPassword().equalsIgnoreCase(password)){
            throw new InvalidPasswordException("Wrong Password Entered!");
        }
        return user;
    }

}
