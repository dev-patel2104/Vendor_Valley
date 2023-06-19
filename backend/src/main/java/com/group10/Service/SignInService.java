package com.group10.Service;

import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;
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
        if(user == null)
        {
            return false;
        }
        if(user.getFirstName() == null || user.getFirstName().isEmpty())
        {
            return false;
        }
        if(user.getLastName() == null || user.getLastName().isEmpty())
        {
            return false;
        }
        if(user.getMobile() == null || user.getMobile().isEmpty())
        {
            return false;
        }
        if(user.getVendor() != 0 && user.getVendor() != 1)
        {
            return false;
        }
        if(user.getStreet() == null || user.getStreet().isEmpty())
        {
            return false;
        }
        if(user.getCity() == null || user.getCity().isEmpty())
        {
            return false;
        }
        if(user.getProvince() == null || user.getProvince().isEmpty())
        {
            return false;
        }
        if(user.getCountry() == null || user.getCountry().isEmpty())
        {
            return false;
        }
        if(user.getEmail() == null || user.getEmail().isEmpty())
        {
            return false;
        }
        if(user.getPassword() == null || user.getPassword().length() < 8 || user.getPassword().isEmpty())
        {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if(!user.getEmail().matches(emailRegex))
        {
            return false;
        }
        if(userRepository.addUser(user))
        {
            return true;
        }

        else
        {
            throw new UserAlreadyPresentException("The user is already present");
        }

    }
}
