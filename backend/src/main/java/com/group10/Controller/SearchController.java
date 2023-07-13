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

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

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
            // Todo: Call search service
            List<Service> services = searchService.getSearchResults(searchParam);
            
            return ResponseEntity.ok(services);
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

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
            // Todo: Call search service
            services = searchService.sortSearchResults(services, sortParam, sortOrder);
            
            return ResponseEntity.ok(services);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

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
            
            Map<String, String> filterParamMap = new ObjectMapper().readValue(filterParam, new TypeReference<Map<String, String>>(){});

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
