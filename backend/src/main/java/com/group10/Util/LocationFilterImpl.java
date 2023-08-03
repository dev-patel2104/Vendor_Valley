package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;

public class LocationFilterImpl implements IFilter {

    private String location;

    public LocationFilterImpl(String location) {
        this.location = location;
    }

    @Override
    public List<Service> execute(List<Service> services) {
        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (service.getCompanyProvince().contains(location)) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }
}
