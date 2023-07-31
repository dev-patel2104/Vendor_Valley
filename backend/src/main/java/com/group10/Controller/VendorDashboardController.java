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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;
import com.group10.Service.HomeServiceImpl;

@RestController
public class VendorDashboardController {

    @Autowired
    private HomeServiceImpl homeService;
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getStatistics")
    public ResponseEntity<VendorDashboard> getStatistics(@RequestHeader String jwtToken, @RequestBody Map<String, String> body) 
    {
        // Get statistics from bookings, reviews, and users
        if (jwtToken == null || jwtToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            VendorDashboard vendorDashboard = homeService.getVendorDashboardInfo(jwtToken);
            if (vendorDashboard == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return ResponseEntity.ok(vendorDashboard);
        } 
        catch (SQLException | JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getCustomerInfo")
    public ResponseEntity<List<SignUpModel>> getCustomerInformation(@RequestBody List<Integer> body){
        List<Integer> userIds = body;
        if (userIds == null || userIds.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        for (Integer userId : userIds) {
            if (userId == null || userId == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        try {
            List<SignUpModel> users = homeService.getCustomerInfo(userIds);
            if (users == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            return ResponseEntity.ok(users);
        } 
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
