package com.group10.Controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.group10.Model.Service;
import com.group10.Service.SearchService;

/**
 * Controller class for handling search requests.
 */
@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * Retrieves search results based on the provided search parameter.
     *
     * @param body A map containing the search parameter.
     * @return A ResponseEntity object containing a list of Service objects if the search is successful,
     *         or an error response if there is an issue with the request or the server.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/search")
    public ResponseEntity<List<Service>> getSearchResults(@RequestBody Map<String, String> body)
    {   
        String searchParam = body.get("searchParam");
        if (searchParam == null || searchParam.equals(""))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try
        {
            
            /**
             * Retrieves a list of services based on the provided search parameter.
             *
             * @param searchParam The search parameter used to filter the services.
             * @return A list of services that match the search parameter.
             */
            List<Service> services = searchService.getSearchResults(searchParam);
            
            return ResponseEntity.ok(services);
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * Endpoint for sorting a list of services based on the provided sort parameter and sort order.
     *
     * @param services The list of services to be sorted
     * @param sortParam The parameter to sort the services by
     * @param sortOrder The order in which to sort the services (ascending or descending)
     * @return A ResponseEntity containing the sorted list of services or an appropriate error response
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/sort")
    public ResponseEntity<List<Service>> getSortedResults(@RequestBody List<Service> services, @RequestParam String sortParam, @RequestParam String sortOrder)
    {   
        if (services == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (sortParam == null || sortParam == "" || services.size() == 0)
        {
            return ResponseEntity.ok(services);
        }
        try
        {
            
            /**
             * Sorts the list of services based on the specified sort parameter and sort order.
             *
             * @param services The list of services to be sorted
             * @param sortParam The parameter to sort the services by
             * @param sortOrder The order in which to sort the services (ascending or descending)
             * @return The sorted list of services
             */
            services = searchService.sortSearchResults(services, sortParam, sortOrder);
            
            return ResponseEntity.ok(services);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * Retrieves filtered results based on the provided filter parameter.
     *
     * @param services The list of services to filter
     * @param filterParam The filter parameter to apply
     * @return A ResponseEntity containing the filtered list of services, or an appropriate error response
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/filter")
    public ResponseEntity<List<Service>> getFilteredResults(@RequestBody List<Service> services, @RequestParam String filterParam)
    {   

        try {
            if (services == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if (filterParam == null || filterParam == "" || services.size() == 0)
            {
                return ResponseEntity.ok(services);
            }
            
            /**
             * Converts a JSON string into a Map<String, String> object using the Jackson ObjectMapper.
             *
             * @param filterParam The JSON string to be converted.
             * @return A Map<String, String> object representing the JSON data.
             * @throws IOException If there is an error reading the JSON string.
             */
            Map<String, String> filterParamMap = new ObjectMapper().readValue(filterParam, new TypeReference<Map<String, String>>(){});

            /**
             * Filters the search results based on the given filter parameters.
             *
             * @param services The list of services to be filtered
             * @param filterParamMap The map of filter parameters
             * @return The filtered list of services
             */
            services = searchService.filterSearchResults(services, filterParamMap);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(services);
    }
}
