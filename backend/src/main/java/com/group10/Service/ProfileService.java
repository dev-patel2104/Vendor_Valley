package com.group10.Service;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;
import com.group10.Repository.UserRepository;
import com.group10.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public abstract class ProfileService
{
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected VendorRepository vendorRepository;

    public SignUpModel getProfile(int user_id) throws SQLException, UserDoesntExistException {
        if(user_id < 0)
        {
            throw new UserDoesntExistException("No such user exist");
        }
        SignUpModel user;
        user = userRepository.getUser(user_id);
        if(user == null)
        {
            throw new UserDoesntExistException("No such user exist");
        }
        return user;
    }

    public abstract List<Booking> getBookings(int userId) throws UserDoesntExistException, SQLException;

    public abstract boolean editProfile(int userId) throws SQLException;
}
