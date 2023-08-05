package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;
import lombok.extern.slf4j.Slf4j;


/**
 * A filter implementation to filter services based on their average ratings.
 */
@Slf4j
public class RatingsFilterImpl implements IFilter {

    private double value;

    /**
     * Constructs a RatingsFilterImpl with the specified minimum rating value.
     *
     * @param value The minimum average rating value to filter services.
     */
    public RatingsFilterImpl(double value) {
        this.value = value;
    }

    /**
     * Filters a list of services based on their average ratings.
     *
     * @param services The list of services to be filtered.
     * @return A filtered list of services with average ratings greater than or equal to the specified value.
     */
    @Override
    public List<Service> execute(List<Service> services) {
        log.debug("Filtering services based on ratings >= " + value);

        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            try {
                double avgRating = Double.parseDouble(service.getAverageRating());
                if (avgRating >= value) {
                    filteredServices.add(service);
                }
            } catch (NumberFormatException | NullPointerException e) {
                log.warn("Error parsing average rating for service " + service.getServiceName() + ": " + e.getMessage());
            }
        }

        log.debug("Filtered services count: " + filteredServices.size());
        return filteredServices;
    }
}