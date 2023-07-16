package com.group10.Service;

import com.group10.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Qualifier("VendorProfileService")
public class VendorProfileService extends ProfileService
{

    @Autowired
    private ServiceRepository serviceRepository;
    @Override
    public void getBookings() {

    }

    public List<com.group10.Model.Service> getServices(int user_id) throws SQLException
    {
        return serviceRepository.getServicesForVendor(user_id);
    }
}
