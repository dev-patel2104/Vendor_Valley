package com.group10.Service;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class VendorProfileService extends ProfileService
{

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Override
    public List<Booking> getBookings(int userId) throws UserDoesntExistException, SQLException
    {
        if(userId < 0)
        {
            throw new UserDoesntExistException("No such user is present");
        }
        return vendorRepository.getBookingsInfo(userId);
    }

    public List<com.group10.Model.Service> getServices(int userId) throws UserDoesntExistException, SQLException
    {
        if(userId < 0)
        {
            throw new UserDoesntExistException("No such user is present");
        }
        return serviceRepository.getServicesForVendor(userId);
    }
}
