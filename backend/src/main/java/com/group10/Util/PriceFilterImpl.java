package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;
import lombok.extern.slf4j.Slf4j;


/**
 * A filter implementation to filter services by price.
 */
@Slf4j
public class PriceFilterImpl implements IFilter {

    private int value;

    /**
     * Constructs a PriceFilterImpl with the specified price value.
     *
     * @param value The maximum price value for filtering services.
     */
    public PriceFilterImpl(int value) {
        this.value = value;
    }

    /**
     * Filters the given list of services based on their price.
     *
     * @param services The list of services to be filtered.
     * @return A list of services with prices less than or equal to the specified value.
     */
    @Override
    public List<Service> execute(List<Service> services) {
        log.debug("Filtering services by price less than or equal to: " + value);

        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            try {
                int servicePrice = Integer.parseInt(service.getServicePrice());
                if (servicePrice <= value) {
                    filteredServices.add(service);
                }
            } catch (NumberFormatException | NullPointerException e) {
                log.warn("Error parsing price for service " + service.getServiceName() + ": " + e.getMessage());
            }
        }

        log.debug("Filtered services count: " + filteredServices.size());
        return filteredServices;
    }
}