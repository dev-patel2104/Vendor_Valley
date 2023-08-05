package com.group10.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group10.Repository.Interfaces.IImageRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.group10.Constants.Constants;
import com.group10.Model.Service;
import com.group10.Service.Interfaces.ISearchService;
import com.group10.Util.BookingsComparator;
import com.group10.Util.BookingsFilterImpl;
import com.group10.Util.CategoryFilterImpl;
import com.group10.Util.ComparatorUtil;
import com.group10.Util.LocationFilterImpl;
import com.group10.Util.PriceComparator;
import com.group10.Util.PriceFilterImpl;
import com.group10.Util.RatingComparator;
import com.group10.Util.RatingsFilterImpl;
import com.group10.Util.Interfaces.IFilter;

/**
 * Implementation of the ISearchService interface that provides search functionality.
 * This service is responsible for retrieving search results based on a search parameter.
 */
@org.springframework.stereotype.Service
@Slf4j
public class SearchServiceImpl implements ISearchService{
    @Autowired
    private IServiceRepository searchRepository;


    @Autowired
    private IImageRepository serviceImageRepository;
    
    /**
     * Retrieves a list of services based on the provided search parameter.
     *
     * @param searchParam The search parameter to filter the services.
     * @return A list of services that match the search parameter.
     * @throws SQLException If there is an error retrieving the services.
     */
    @Override
    public List<Service> getSearchResults(String searchParam) throws SQLException {
        try {
            // Retrieve a list of services based on the search parameter
            List<Service> servicesList = searchRepository.getServicesBasedOnSearchParam(searchParam);

            // Retrieve images for the services based on the search parameter
            List<Service> servicesWithImages = serviceImageRepository.getImagesForService(servicesList, searchParam);

            log.info("Retrieved search results for parameter: " + searchParam);
            return servicesWithImages;
        } catch (SQLException e) {
            log.error("Error occurred while retrieving search results: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Sorts a list of services based on a given sort parameter and sort order.
     *
     * @param services The list of services to be sorted
     * @param sortParam The parameter to sort the services by (e.g., "price", "rating", "bookings")
     * @param sortOrder The order in which to sort the services (true for ascending, false for descending)
     * @return The sorted list of services
     */
    public List<Service> sortSearchResults(List<Service> services, String sortParam, Boolean sortOrder) {
        if (sortOrder == null) {
            sortOrder = Constants.ASC;
        }
        ComparatorUtil comparatorUtilObj;

        if (sortParam == null || sortParam.isEmpty()) {
            return services;
        }

        if (sortParam.equalsIgnoreCase(Constants.PRICE)) {
            comparatorUtilObj = new ComparatorUtil(new PriceComparator(), sortOrder);
        } else if (sortParam.equalsIgnoreCase(Constants.RATING)) {
            comparatorUtilObj = new ComparatorUtil(new RatingComparator(), sortOrder);
        } else if (sortParam.equalsIgnoreCase(Constants.BOOKINGS)) {
            comparatorUtilObj = new ComparatorUtil(new BookingsComparator(), sortOrder);
        } else {
            return services;
        }

        // Perform sorting using the specified comparator and order
        comparatorUtilObj.sort(services);

        log.info("Sorted search results based on parameter: " + sortParam + ", Order: " + sortOrder);
        return services;
    }
    

    /**
     * Filters a list of services based on the provided filter values.
     *
     * @param services The list of services to filter
     * @param filterValues A map of filter keys and values
     * @return The filtered list of services
     */
    public List<Service> filterSearchResults(List<Service> services, Map<String, String> filterValues) {
        if (filterValues == null || filterValues.isEmpty()) {
            return services;
        }

        List<Service> filteredServices = new ArrayList<>();
        for (Map.Entry<String, String> entry : filterValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            IFilter filterUtilObj = null;

            if (key.equalsIgnoreCase(Constants.PRICE)) {
                filterUtilObj = new PriceFilterImpl(Integer.parseInt(value));
            } else if (key.equalsIgnoreCase(Constants.RATING)) {
                filterUtilObj = new RatingsFilterImpl(Double.parseDouble(value));
            } else if (key.equalsIgnoreCase(Constants.BOOKINGS)) {
                filterUtilObj = new BookingsFilterImpl(Integer.parseInt(value));
            } else if (key.equalsIgnoreCase(Constants.CATEGORY)) {
                filterUtilObj = new CategoryFilterImpl(value);
            } else if (key.equalsIgnoreCase(Constants.LOCATION)) {
                filterUtilObj = new LocationFilterImpl(value);
            } else {
                return services;
            }

            // Apply the filter and update the filteredServices list
            filteredServices = filterUtilObj.execute(filteredServices.isEmpty() ? services : filteredServices);

            log.info("Filtered search results based on filter: " + key + "=" + value);
        }
        return filteredServices;
    }
}
