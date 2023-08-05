package com.group10.Controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.group10.Service.Interfaces.ISearchService;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Controller class for handling search requests.
 */
@RestController
@Slf4j
public class SearchController {
    @Autowired
    private ISearchService searchService;

    /**
     * Retrieves search results based on the provided search parameter.
     *
     * @param body A map containing the search parameter.
     * @return A ResponseEntity object containing a list of Service objects if the search is successful,
     *         or an error response if there is an issue with the request or the server.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/search")
    public ResponseEntity<List<Service>> getSearchResults(@RequestBody Map<String, String> body) {
        log.info("Received search request: {}", body);

        String searchParam = body.get("searchParam");
        if (searchParam == null || searchParam.equals("")) {
            log.warn("Invalid search request: empty or missing search parameter");
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
            log.info("Search successful, returning {} results", services.size());
            return ResponseEntity.ok(services);
        } catch (SQLException e) {
            log.error("SQLException occurred while processing search request.", e);
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
    public ResponseEntity<List<Service>> getSortedResults(
            @RequestBody List<Service> services,
            @RequestParam String sortParam,
            @RequestParam Boolean sortOrder) {
        log.info("Received sort request");

        if (services == null) {
            log.warn("Invalid sort request: null list of services");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (sortParam == null || sortParam.equals("") || services.size() == 0) {
            log.warn("Invalid sort request: missing sort parameter or empty service list");
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
            log.info("Sort successful");
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            log.error("Error occurred while processing sort request.", e);
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
    public ResponseEntity<List<Service>> getFilteredResults(
            @RequestBody List<Service> services,
            @RequestParam String filterParam) {
        log.info("Received filter request: {}", services);

        try {
            if (services == null) {
                log.warn("Invalid filter request: null list of services");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (filterParam == null || filterParam.equals("") || services.size() == 0) {
                log.warn("Invalid filter request: missing filter parameter or empty service list");
                return ResponseEntity.ok(services);
            }

            log.info("Converting filter parameter JSON to Map");
            Map<String, String> filterParamMap = new ObjectMapper().readValue(filterParam, new TypeReference<Map<String, String>>(){});
            log.info("Filtering services based on filter parameters");
            services = searchService.filterSearchResults(services, filterParamMap);
            log.info("Filtering successful");
        } catch (JsonProcessingException e) {
            log.error("Error occurred while processing filter request (JSON processing)", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            log.error("Error occurred while processing filter request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(services);
    }
}
