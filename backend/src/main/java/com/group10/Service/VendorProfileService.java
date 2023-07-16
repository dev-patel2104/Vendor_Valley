package com.group10.Service;

import com.group10.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("VendorProfileService")
public class VendorProfileService extends ProfileService
{

    @Override
    public void getBookings() {

    }

    public List<Service> getServices()
    {
        return null;
    }
}
