package com.group10.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Model.SignUpModel;
import com.group10.Service.CustomerProfileService;
import com.group10.Service.VendorProfileService;
import com.group10.Util.JWTTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProfileController
{

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private VendorProfileService vendorProfileService;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;
    private List<Category> categories;
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/profile")
    public ResponseEntity<SignUpModel> getProfile(@RequestParam String jwtToken)
    {
        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        SignUpModel user;
        try {
           user = customerProfileService.getProfile(token.getClaim("userId").asInt());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (UserDoesntExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices(@RequestParam String jwtToken)
    {
        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        List<Service> serviceList = new ArrayList<>();
        try {
            serviceList = vendorProfileService.getServices(token.getClaim("userId").asInt());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (UserDoesntExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok().body(serviceList);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(@RequestParam String jwtToken)
    {
        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        List<Booking> bookingList = new ArrayList<>();
        try
        {
            bookingList = vendorProfileService.getBookings(token.getClaim("userId").asInt());
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (UserDoesntExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        return ResponseEntity.ok(bookingList);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories()
    {
        try
        {
            categories = vendorProfileService.getCategories();
        }
        catch (SQLException e )
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(categories);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addService")
    public ResponseEntity<String> addService(@RequestBody Service service)
    {
        try
        {
            if(service == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Input not mapped to the body");
            }

            if(!vendorProfileService.addService(service,categories))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data is not processable");
            }

        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database issue present");

        }
        return ResponseEntity.ok("Service successfully added");
    }

}
