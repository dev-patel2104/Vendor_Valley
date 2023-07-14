package com.group10.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group10.Model.Review;
import com.group10.Service.CustomerSelectsVendorService;

@RestController
public class CustomerSelectsVendorController {
    
    @Autowired
    private CustomerSelectsVendorService customerSelectsVendorService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getReviews")
    public ResponseEntity<List<Review>> getReviews(@RequestBody Map<String, String> body) 
    {   
        String serviceId = body.get("serviceId");
        if (serviceId == null || serviceId.equals(""))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try
        {
            // Todo: Call search service
            List<Review> reviews = new ArrayList<>();
            reviews = customerSelectsVendorService.getReviews(serviceId);
            
            return ResponseEntity.ok(reviews);
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
