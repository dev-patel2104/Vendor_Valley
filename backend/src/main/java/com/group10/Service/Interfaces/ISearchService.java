package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.group10.Model.Service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Interface for search-related services.
 */
public interface ISearchService {

    /**
     * Retrieves a list of service objects based on the provided search parameter.
     *
     * @param searchParam The search parameter used to filter the results.
     * @return A list of service objects that match the search parameter.
     * @throws SQLException If an error occurs while retrieving the search results from the database.
     */
    List<Service> getSearchResults(String searchParam) throws SQLException;

    /**
     * Sorts the list of service objects based on the provided sort parameter and order.
     *
     * @param services   The list of service objects to be sorted.
     * @param sortParam  The parameter used for sorting.
     * @param sortOrder  The order in which the list should be sorted (true for ascending, false for descending).
     * @return A sorted list of service objects.
     */
    List<Service> sortSearchResults(List<Service> services, String sortParam, Boolean sortOrder);

    /**
     * Filters the list of service objects based on the provided filter values.
     *
     * @param services    The list of service objects to be filtered.
     * @param filterValues A map of filter parameters and their corresponding values.
     * @return A filtered list of service objects.
     */
    List<Service> filterSearchResults(List<Service> services, Map<String, String> filterValues);
}

