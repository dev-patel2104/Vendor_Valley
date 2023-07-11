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
import org.springframework.web.bind.annotation.RestController;
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

}
