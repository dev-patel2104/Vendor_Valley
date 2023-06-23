package com.group10.Service;

import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;
import com.group10.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class SignInService
{
    @Autowired
    private UserRepository userRepository;

    public boolean SignIn(User user) throws UserAlreadyPresentException, SQLException
    {
        if (user == null) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getFirstName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getLastName())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getMobile())) {
            return false;
        }
        if(user.getVendor() != 0 && user.getVendor() != 1)
        {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getStreet())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getCity())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getProvince())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getCountry())) {
            return false;
        }
        if (!StringUtil.isNotNullAndNotEmpty(user.getEmail())) {
            return false;
        }
        if(!StringUtil.isNotNullAndNotEmpty(user.getPassword()) || user.getPassword().length() < 8) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if(!user.getEmail().matches(emailRegex)) {
            return false;
        }

        if(userRepository.addUser(user)) {
            return true;
        } else {
            throw new UserAlreadyPresentException("The user is already present");
        }

    }
}
