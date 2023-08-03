package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;

public class RatingsFilterImpl implements IFilter{
    private double value;

    public RatingsFilterImpl(double value) {
        this.value = value;
    }

    @Override
    public List<Service> execute(List<Service> services) {
        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (Double.parseDouble(service.getAverageRating()) >= value) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }
}

