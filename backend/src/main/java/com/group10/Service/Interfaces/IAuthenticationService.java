package com.group10.Service.Interfaces;

import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;

import java.sql.SQLException;

public interface IAuthenticationService
{
    public boolean SignIn(SignUpModel signUpModel) throws UserAlreadyPresentException, SQLException, VendorDetailsAbsentForUserException;
    public User login(String email, String password) throws UserDoesntExistException, InvalidPasswordException, SQLException;
}
