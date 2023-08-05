package com.group10.Controller;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Service.Interfaces.IHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The HomeController class is a Spring RestController responsible for handling various endpoints related to the home page of the application.
 */
@RestController
@Slf4j
public class HomeController {

    private List<Category> featuredCategories;
    private List<Service> trendingServices;

    @Autowired
    private IHomeService homeService;

    /**
     * Handles the root ("/") endpoint and returns a welcome message.
     *
     * @return ResponseEntity with the welcome message.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping("/")
    public ResponseEntity<String> home() {
        log.debug("Handling root endpoint: /");
        return ResponseEntity.ok("Welcome");
    }

    /**
     * Handles the "/featured" endpoint and retrieves the featured categories that have the highest number of vendors providing services.
     *
     * @return ResponseEntity with the list of featured categories.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/featured")
    public ResponseEntity<List<Category>> getFeaturedCategories() {
        log.debug("Handling /featured endpoint");

        featuredCategories = new ArrayList<>();
        try {
            featuredCategories = homeService.featuredCategories();
            log.info("Retrieved {} featured categories", featuredCategories.size());
        } catch (SQLException e) {
            log.error("SQL exception occurred while retrieving featured categories: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(featuredCategories);
    }

    /**
     * Handles the "/trending" endpoint and retrieves the trending services that have the most number of bookings.
     *
     * @return ResponseEntity with the list of trending services.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/trending")
    public ResponseEntity<List<Service>> getTrendingServices() {
        log.debug("Handling /trending endpoint");

        trendingServices = new ArrayList<>();
        try {
            trendingServices = homeService.TrendingServices();
            log.info("Retrieved {} trending services", trendingServices.size());
        } catch (SQLException e) {
            log.error("SQL exception occurred while retrieving trending services: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(trendingServices);
    }
}
