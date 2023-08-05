package com.group10.Util.Interfaces;

import java.util.List;

import com.group10.Model.Service;


/**
 * Interface for filtering a list of services based on certain criteria.
 */
public interface IFilter {
    /**
     * Executes the filtering operation on a list of services.
     *
     * @param services The list of services to be filtered.
     * @return The filtered list of services based on the implemented criteria.
     */
    List<Service> execute(List<Service> services);
}
