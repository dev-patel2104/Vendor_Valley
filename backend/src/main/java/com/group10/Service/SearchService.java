package com.group10.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.group10.Model.Service;
import com.group10.Repository.ServiceRepository;
import com.group10.Service.Interfaces.ISearchService;

/**
 * Implementation of the ISearchService interface that provides search functionality.
 * This service is responsible for retrieving search results based on a search parameter.
 */
@org.springframework.stereotype.Service
public class SearchService implements ISearchService{
    @Autowired
    private ServiceRepository searchRepository;

    private final String PRICE = "price";
    private final String RATING = "rating";
    private final String BOOKINGS = "bookings";
    private final String CATEGORY = "category";
    private final String ASC = "asc";
    private final String DESC = "desc";

    /**
     * Retrieves a list of services based on the provided search parameter.
     *
     * @param searchParam The search parameter to filter the services.
     * @return A list of services that match the search parameter.
     * @throws SQLException If there is an error retrieving the services.
     */
    @Override
    public List<Service> getSearchResults(String searchParam) throws SQLException {
        try{
            /**
             * Retrieves a list of services based on the given search parameter.
             *
             * @param searchParam The search parameter used to filter the services.
             * @return A list of services that match the search parameter.
             */
            return searchRepository.getServicesBasedOnSearchParam(searchParam);
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    /**
     * Sorts the list of services based on the given sort parameter and sort order.
     *
     * @param services The list of services to be sorted
     * @param sortParam The parameter to sort the services by
     * @param sortOrder The order in which to sort the services ("asc" for ascending, "desc" for descending)
     * @return The sorted list of services
     */
    public List<Service> sortSearchResults(List<Service> services, String sortParam, String sortOrder) {
        // Sort the services based on the sort params one by one
        if (sortOrder == null || sortOrder.equals("")){
            sortOrder = ASC;
        }
        if (sortOrder.equalsIgnoreCase(ASC)){
            services = sortAsc(services, sortParam);
        }
        else if (sortOrder.equalsIgnoreCase(DESC)){
            services = sortDesc(services, sortParam);
        }
        return services;
    }

    /**
     * Sorts a list of services in descending order based on the specified sort parameter.
     *
     * @param services The list of services to be sorted
     * @param sortParam The parameter to sort the services by (e.g. "price")
     * @return The sorted list of services in descending order
     */
    private List<Service> sortDesc(List<Service> services, String sortParam) {
        // Sort the services based on the sort param
        if(sortParam == null || sortParam.equals(""))
        {
            return services;
        }

        if (sortParam.equalsIgnoreCase(PRICE)){   
            // Sort by price
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getServicePrice()) < Integer.parseInt(services.get(j).getServicePrice()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase(RATING)){
            // Sort by rating
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Double.parseDouble(services.get(i).getAverageRating()) < Double.parseDouble(services.get(j).getAverageRating()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase(BOOKINGS)){
            // Sort by bookings
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getTotalBookings()) < Integer.parseInt(services.get(j).getTotalBookings()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        return services;
    }

    /**
     * Sorts a list of services in ascending order based on the specified sort parameter.
     *
     * @param services The list of services to be sorted
     * @param sortParam The parameter to sort the services by (e.g. "price")
     * @return The sorted list of services
     */
    private List<Service> sortAsc(List<Service> services, String sortParam) {
        // Sort the services based on the sort param
        if(sortParam == null || sortParam.equals(""))
        {
            return services;
        }

        if (sortParam.equalsIgnoreCase(PRICE)){   
            // Sort by price
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getServicePrice()) > Integer.parseInt(services.get(j).getServicePrice()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase(RATING)){
            // Sort by rating
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Double.parseDouble(services.get(i).getAverageRating()) > Double.parseDouble(services.get(j).getAverageRating()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase(BOOKINGS)){
            // Sort by bookings
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getTotalBookings()) > Integer.parseInt(services.get(j).getTotalBookings()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        return services;
    }
    
    /**
     * Filters the search results based on the provided filter values.
     *
     * @param services The list of services to filter
     * @param filterValues A map of filter keys and values
     * @return The filtered list of services
     */
    public List<Service> filterSearchResults(List<Service> services, Map<String, String> filterValues){
        // Filter the services based on the filter params one by one
        if (filterValues == null || filterValues.size() == 0){
            return services;
        }
        for (Map.Entry<String, String> entry : filterValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            services = filterSearchResults(services, key, value);
        }
        return services;
    }

    /**
     * Filters the search results based on the given key and value.
     *
     * @param services The list of services to filter
     * @param key The key to filter on
     * @param value The value to filter on
     * @return The filtered list of services
     */
    private List<Service> filterSearchResults(List<Service> services, String key, String value) {
        // Filter the services based on the filter param
        if(key == null || key.equals("") || value == null || value.equals(""))
        {
            return services;
        }
        // Filter process
        if (key.equalsIgnoreCase(PRICE)){
            // Filter by price
            for (int i = 0; i < services.size(); i++)
            {
                if (Integer.parseInt(services.get(i).getServicePrice()) > Integer.parseInt(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase(RATING)){
            // Filter by rating
            for (int i = 0; i < services.size(); i++)
            {
                if (Double.parseDouble(services.get(i).getAverageRating()) < Double.parseDouble(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase(BOOKINGS)){
            // Filter by bookings
            for (int i = 0; i < services.size(); i++)
            {
                if (Integer.parseInt(services.get(i).getTotalBookings()) < Integer.parseInt(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase(CATEGORY)){
            // Filter by category
            for (int i = 0; i < services.size(); i++)
            {
                if (!services.get(i).getCategoryNames().contains(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        return services;
    }
}
