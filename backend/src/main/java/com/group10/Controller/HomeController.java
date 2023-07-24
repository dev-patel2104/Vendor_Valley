package com.group10.Controller;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Service.Interfaces.IHomeService;
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

@RestController
public class HomeController {

    private List<Category> featuredCategories;
    private List<Service> trendingServices;
    @Autowired
    private IHomeService homeService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome");
    }

    // category that has the highest number of vendors providing services
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/featured")
    public ResponseEntity<List<Category>> getFeaturedCategories()
    {
        featuredCategories = new ArrayList<>();
        try
        {
            featuredCategories = homeService.featuredCategories();
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(featuredCategories);
    }

    // Services that has the most number of bookings
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/trending")
    public ResponseEntity<List<Service>> getTrendingServices()
    {
        trendingServices = new ArrayList<>();
        try
        {
            trendingServices = homeService.TrendingServices();
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(trendingServices);
    }

}
