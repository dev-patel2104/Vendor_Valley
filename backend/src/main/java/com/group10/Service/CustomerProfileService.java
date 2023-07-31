package com.group10.Service;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Repository.Interfaces.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerProfileService extends ProfileService
{
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public List<Booking> getBookings(int userId) throws SQLException, UserDoesntExistException
    {
        if(userId < 0)
        {
            throw new UserDoesntExistException("No information for the given Id  exists");
        }
        return customerRepository.getBookings(userId);
    }

}
