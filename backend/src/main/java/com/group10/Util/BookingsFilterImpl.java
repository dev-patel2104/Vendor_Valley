package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;
import lombok.extern.slf4j.Slf4j;


/**
 * Implementation of the IFilter interface for filtering services based on total bookings.
 */
@Slf4j
public class BookingsFilterImpl implements IFilter {

    private int value;

    /**
     * Constructs a BookingsFilterImpl with the provided value.
     *
     * @param value The minimum value for total bookings to include in the filter.
     */
    public BookingsFilterImpl(int value) {
        this.value = value;
    }

    /**
     * Filters the list of services based on the minimum total bookings.
     *
     * @param services The list of services to be filtered.
     * @return A list of services that meet the filtering criteria.
     */
    @Override
    public List<Service> execute(List<Service> services) {
        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (Integer.parseInt(service.getTotalBookings()) >= value) {
                filteredServices.add(service);
                log.debug("Service " + service.getServiceName() + " passed the bookings filter.");
            } else {
                log.debug("Service " + service.getServiceName() + " did not pass the bookings filter.");
            }
        }
        return filteredServices;
    }
}
