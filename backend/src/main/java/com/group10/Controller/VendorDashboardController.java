package com.group10.Controller;

import java.sql.SQLException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
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
import com.group10.Service.Interfaces.IHomeService;

/**
 * The VendorDashboardController class is a Spring RestController responsible for handling endpoints related to the vendor's dashboard and customer information.
 */
@RestController
@Slf4j
public class VendorDashboardController {

    @Autowired
    private IHomeService homeService;

    /**
     * Handles the "/getStatistics" endpoint and retrieves statistics for the vendor's dashboard.
     *
     * @param jwtToken The JWT token obtained from the request header.
     * @return ResponseEntity with the vendor's dashboard information.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getStatistics")
    public ResponseEntity<VendorDashboard> getStatistics(@RequestHeader String jwtToken) {
        log.info("Received request for vendor statistics");

        if (jwtToken == null || jwtToken.isEmpty()) {
            log.warn("Missing or empty JWT token");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            log.info("Getting vendor statistics from services");
            VendorDashboard vendorDashboard = homeService.getVendorDashboardInfo(jwtToken);

            if (vendorDashboard == null) {
                log.warn("No content found for vendor statistics");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            log.info("Returning vendor statistics");
            return ResponseEntity.ok(vendorDashboard);
        }
        catch (SQLException | JWTVerificationException e) {
            log.error("Error occurred while fetching vendor statistics", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Handles the "/getCustomerInfo" endpoint and retrieves customer information for the provided list of user IDs.
     *
     * @param body The list of user IDs for which customer information is requested.
     * @return ResponseEntity with the list of customer information.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getCustomerInfo")
    public ResponseEntity<List<SignUpModel>> getCustomerInformation(@RequestBody List<Integer> body) {
        log.info("Received request for customer information");

        List<Integer> userIds = body;
        if (userIds == null || userIds.size() == 0) {
            log.warn("Invalid or empty user IDs provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        for (Integer userId : userIds) {
            if (userId == null || userId == 0) {
                log.warn("Invalid user ID found in the list");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }

        try {
            log.info("Fetching customer information from services");
            List<SignUpModel> users = homeService.getCustomerInfo(userIds);

            if (users == null) {
                log.warn("No content found for customer information");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            log.info("Returning customer information");
            return ResponseEntity.ok(users);
        }
        catch (SQLException e) {
            log.error("Error occurred while fetching customer information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
