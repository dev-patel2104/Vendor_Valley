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

    public com.group10.Model.Service addService(com.group10.Model.Service service, List<Category> categoryList) throws SQLException, NoInformationFoundException
    {
        if(service.getServiceName() == null || service.getServiceName().isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");
        }
        else if(service.getServiceDescription() == null || service.getServiceDescription().isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");

        }
        else if(service.getServicePrice() == null || service.getServicePrice().isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");

        }
        else if(service.getCategoryNames() == null || service.getCategoryNames().isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");

        }
        else if(service.getImages() == null || service.getImages().isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");

        }
        if(categoryList == null || categoryList.isEmpty())
        {
            throw new NoInformationFoundException("service name is absent");

        }

        return serviceRepository.insertService(service, categoryList);
    }

    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException, NoInformationFoundException
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
