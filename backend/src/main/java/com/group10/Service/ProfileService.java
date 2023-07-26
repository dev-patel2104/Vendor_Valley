package com.group10.Service;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Repository.CustomerRepositoryImpl;
import com.group10.Repository.VendorRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public abstract class ProfileService
{
    @Autowired
    protected CustomerRepositoryImpl CustomerRepositoryImpl;
    @Autowired
    protected VendorRepositoryImpl VendorRepositoryImpl;

    public SignUpModel getProfile(int user_id) throws SQLException, UserDoesntExistException {
        if(user_id < 0)
        {
            throw new UserDoesntExistException("No such user exist");
        }
        SignUpModel user;
        List<Integer> userIds = List.of(user_id);
        List<SignUpModel> users = CustomerRepositoryImpl.getUsers(userIds);
        // We are only concerned about the first user in the list
        if(users==null || users.size() == 0)
        {
            throw new UserDoesntExistException("No such user exist");
        }
        user = users.get(0);
        return user;
    }

    public boolean editProfile(SignUpModel newInfo) throws SQLException, NoInformationFoundException
    {
        User user;
        if(newInfo == null)
        {
            throw new NoInformationFoundException("Requested input is missing");
        }
        if(newInfo.getUserId() < 0)
        {
            throw  new NoInformationFoundException("The userId for the user to be updated is not available");
        }
        user = newInfo.buildUserModel();
        user.setUserId(newInfo.getUserId());
        return CustomerRepositoryImpl.updateUser(user);
    }

    public abstract List<Booking> getBookings(int userId) throws UserDoesntExistException, SQLException;


}
