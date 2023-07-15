package com.group10.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group10.Model.Review;

@RestController
public class VendorDashboardController {
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getStatistics")
    public ResponseEntity<List<Review>> getStatistics(@RequestBody Map<String, String> body) 
    {
        
        return null;
    }

}
