package com.group10.Service;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.Category;
import com.group10.Model.SignUpModel;
import com.group10.Repository.CategoryRepository;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VendorProfileService extends ProfileService
{

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
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

    public List<Category> getCategories() throws SQLException
    {
        return categoryRepository.getCategories();
    }

    public boolean addService(com.group10.Model.Service service, List<Category> categoryList) throws SQLException
    {
        if(service.getServiceName() == null || service.getServiceName().isEmpty())
        {
            return false;
        }
        else if(service.getServiceDescription() == null || service.getServiceDescription().isEmpty())
        {
            return false;
        }
        else if(service.getServicePrice() == null || service.getServicePrice().isEmpty())
        {
            return false;
        }
        else if(service.getCategoryNames() == null || service.getCategoryNames().isEmpty())
        {
            return false;
        }
        else if(service.getImages() == null || service.getImages().isEmpty())
        {
            return false;
        }
        if(categoryList == null || categoryList.isEmpty())
        {
            return false;
        }

        return serviceRepository.insertService(service, categoryList);
    }

    @Override
    public boolean editProfile(int userId) throws SQLException {
        return false;
    }

    public boolean editCompanyDetails(SignUpModel updatedDetails) throws SQLException, NoInformationFoundException
    {
        if(updatedDetails == null)
        {
            throw new NoInformationFoundException("Requested input is missing");
        }
        if(updatedDetails.getUserId() < 0)
        {
            throw new NoInformationFoundException("The userId for the user to be updated is not available");
        }
        return vendorRepository.editCompanyDetails(updatedDetails);
    }
}
