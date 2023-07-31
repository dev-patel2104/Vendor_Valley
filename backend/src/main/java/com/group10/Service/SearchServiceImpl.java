package com.group10.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group10.Repository.Interfaces.IImageRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.group10.Constants.Constants;
import com.group10.Model.Service;
import com.group10.Service.Interfaces.ISearchService;
import com.group10.Util.BookingsComparator;
import com.group10.Util.BookingsFilterImpl;
import com.group10.Util.CategoryFilterImpl;
import com.group10.Util.ComparatorUtil;
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
        try{
            /**
             * Retrieves a list of services based on the given search parameter.
             *
             * @param searchParam The search parameter used to filter the services.
             * @return A list of services that match the search parameter.
             */
            List<Service> servicesList = searchRepository.getServicesBasedOnSearchParam(searchParam);
            
            /**
             * Retrieves images for a given service and search parameter.
             *
             * @param servicesList A list of services to search for images.
             * @param searchParam The search parameter to use when retrieving images.
             * @return A list of images related to the given service and search parameter.
             */
            return serviceImageRepository.getImagesForService(servicesList, searchParam);
        }
        catch (SQLException e)
        {
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
        // Sort the services based on the sort params one by one
        if (sortOrder == null ){
            sortOrder = Constants.ASC;
        }
        ComparatorUtil comparatorUtilObj;
    
        if (sortParam == null || sortParam.isEmpty()){
            return services;
        }
        if (sortParam.equalsIgnoreCase(Constants.PRICE)){
            comparatorUtilObj = new ComparatorUtil(new PriceComparator(), sortOrder);
        }
        else if (sortParam.equalsIgnoreCase(Constants.RATING)){
            comparatorUtilObj = new ComparatorUtil(new RatingComparator(), sortOrder);
        }
        else if (sortParam.equalsIgnoreCase(Constants.BOOKINGS)){
            comparatorUtilObj = new ComparatorUtil(new BookingsComparator(), sortOrder);
        }
        else {
            return services;
        }
        comparatorUtilObj.sort(services);   
        return services;
    }
    

    /**
     * Filters a list of services based on the provided filter values.
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
        List<Service> filteredServices = new ArrayList<>();
        for (Map.Entry<String, String> entry : filterValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            IFilter filterUtilObj = null;  
            if (key.equalsIgnoreCase(Constants.PRICE)) {
                filterUtilObj = new PriceFilterImpl(Integer.parseInt(value));
            } 
            else if (key.equalsIgnoreCase(Constants.RATING)) {
                filterUtilObj = new RatingsFilterImpl(Double.parseDouble(value));
            } 
            else if (key.equalsIgnoreCase(Constants.BOOKINGS)) {
                filterUtilObj = new BookingsFilterImpl(Integer.parseInt(value));
            } 
            else if (key.equalsIgnoreCase(Constants.CATEGORY)) {
                filterUtilObj = new CategoryFilterImpl(value);
            }
            else {
                return services;
            }
            filteredServices = filterUtilObj.execute(services);
        }
        return filteredServices;
    }

    public List<Service> sortSearchResults(List<Service> services, String sortParam, String sortOrder)
    {
        return null;
    }
}
