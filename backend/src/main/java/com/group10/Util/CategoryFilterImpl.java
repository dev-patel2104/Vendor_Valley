package com.group10.Util;

import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Service;
import com.group10.Util.Interfaces.IFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryFilterImpl implements IFilter {

    private String value;

    public CategoryFilterImpl(String value) {
        this.value = value;
    }

    /**
     * Executes filtering of services based on the provided category value.
     *
     * @param services The list of services to be filtered.
     * @return A list of services that match the provided category value.
     */
    @Override
    public List<Service> execute(List<Service> services) {
        log.debug("Filtering services by category: " + value);

        List<Service> filteredServices = new ArrayList<>();
        for (Service service : services) {
            if (service.getCategoryNames().contains(value)) {
                filteredServices.add(service);
            }
        }

        log.debug("Filtered services count: " + filteredServices.size());
        return filteredServices;
    }
}