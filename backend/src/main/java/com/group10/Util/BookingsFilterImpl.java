package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;

public class BookingsFilterImpl implements IFilter{
    private int value;

    public BookingsFilterImpl(int value) {
        this.value = value;
    }

    @Override
    public List<Service> execute(List<Service> services) {
        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (Integer.parseInt(service.getTotalBookings()) >= value) {
                filteredServices.add(service);
            }
        }
        return filteredServices;
    }
}