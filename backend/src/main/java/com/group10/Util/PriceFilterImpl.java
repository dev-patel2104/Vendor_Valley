package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;

public class PriceFilterImpl implements IFilter {
    private int value;

    public PriceFilterImpl(int value) {
        this.value = value;
    }

    @Override
    public List<Service> execute(List<Service> services) {
        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (Integer.parseInt(service.getServicePrice()) <= value) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }
    
}
