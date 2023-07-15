package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;

import com.group10.Model.Service;

/**
 * Interface for a search service that retrieves search results based on a search parameter.
 * 
 * @throws SQLException if there is an error accessing the database
 */
public interface ISearchService {
    /**
     * Retrieves a list of service objects based on the provided search parameter.
     *
     * @param searchParam The search parameter used to filter the results.
     * @return A list of service objects that match the search parameter.
     * @throws SQLException If an error occurs while retrieving the search results from the database.
     */
    public List<Service> getSearchResults(String searchParam) throws SQLException;
}
