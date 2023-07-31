package com.group10.Controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
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
    private CustomerProfileService userProfileService;

    @Autowired
    private VendorProfileService vendorProfileService;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;
    private List<Category> categories;
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/profile")
    public ResponseEntity<SignUpModel> getProfile(@RequestHeader String jwtToken)
    {
        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        SignUpModel user;
        try {
           user = userProfileService.getProfile(token.getClaim("userId").asInt());
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
    @PutMapping("/edit/profile")
    public ResponseEntity<String> editProfile(@RequestBody SignUpModel newInfo)
    {
        try
        {
            if(newInfo == null)
            {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            if(!userProfileService.editProfile(newInfo))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (NoInformationFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        return ResponseEntity.ok("Successfully edited the user's profile information");

    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/edit/company")
    public ResponseEntity<SignUpModel> editCompanyDetails(@RequestBody SignUpModel updatedDetails)
    {
        SignUpModel changedDetails;
        try
        {
            if(updatedDetails == null)
            {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            changedDetails = vendorProfileService.editCompanyDetails(updatedDetails);
            if(changedDetails == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        catch (NoInformationFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        return ResponseEntity.ok(changedDetails);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/addService")
    public ResponseEntity<Service> addService(@RequestBody Service service)
    {
        Service addedService;
        try
        {
            if(service == null)
            {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            addedService = vendorProfileService.addService(service,categories);
            if(addedService == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        catch (NoInformationFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

        }
        return ResponseEntity.ok(addedService);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/services")
    public ResponseEntity<List<Service>> getServices(@RequestHeader String jwtToken)
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
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        return ResponseEntity.ok().body(serviceList);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/delete/service")
    public ResponseEntity<String> deleteService(@RequestBody Service serviceToDelete)
    {
        try
        {
            if(serviceToDelete == null)
            {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }


            if(!vendorProfileService.deleteService(serviceToDelete))
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        catch (NoInformationFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

        }
        return ResponseEntity.ok("Service has been successfully deleted");
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/edit/service")
    public ResponseEntity<Service> editService(@RequestBody Service serviceToUpdate)
    {
        Service updatedService;
        try
        {
            if(serviceToUpdate == null)
            {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }
            updatedService = vendorProfileService.editService(serviceToUpdate, categories);
            if(updatedService == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        catch (SQLException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        catch (NoInformationFoundException e)
        {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

        }
        return ResponseEntity.ok(updatedService);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(@RequestHeader String jwtToken)
    {
        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        List<Booking> bookingList = new ArrayList<>();
        int isVendor;
        try
        {
            isVendor = token.getClaim("isVendor").asInt();
            if(isVendor == 1)
            {
                bookingList = vendorProfileService.getBookings(token.getClaim("userId").asInt());
            }
            else
            {
                bookingList = userProfileService.getBookings(token.getClaim("userId").asInt());
            }
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
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
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
}
