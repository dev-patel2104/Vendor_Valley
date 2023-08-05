package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocationFilterImpl implements IFilter {

    private String location;

    public LocationFilterImpl(String location) {
        this.location = location;
    }

    /**
     * Filters a list of services based on the provided location.
     *
     * @param services The list of services to filter.
     * @return A list of services that match the provided location.
     */
    @Override
    public List<Service> execute(List<Service> services) {
        log.debug("Applying location filter for: " + location);

        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (service.getCompanyProvince().contains(location)) {
                filteredServices.add(service);
            }
        }

        log.debug("Filtered services count: " + filteredServices.size());
        return filteredServices;
    }
}
