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

/**
 * The ProfileController class is a Spring RestController responsible for handling various endpoints related to user profiles and services.
 */
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

    /**
     * Handles the "/profile" endpoint and retrieves the user's profile information.
     *
     * @param jwtToken The JWT token obtained from the request header.
     * @return ResponseEntity with the user's profile information.
     */
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

    /**
     * Handles the "/edit/profile" endpoint and allows the user to edit their profile information.
     *
     * @param newInfo The new user profile information as a SignUpModel object.
     * @return ResponseEntity with a success message if the profile was successfully edited.
     */
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

    /**
     * Handles the "/edit/company" endpoint and allows the vendor to edit their company details.
     *
     * @param updatedDetails The updated company details as a SignUpModel object.
     * @return ResponseEntity with the updated company details.
     */
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

    /**
     * Handles the "/addService" endpoint and allows the vendor to add a new service.
     *
     * @param service The new service to be added as a Service object.
     * @return ResponseEntity with the added service information.
     */
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

    /**
     * Handles the "/services" endpoint and retrieves the services associated with a vendor.
     *
     * @param jwtToken The JWT token obtained from the request header.
     * @return ResponseEntity with the list of services associated with the vendor.
     */
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

    /**
     * Handles the "/delete/service" endpoint and allows the vendor to delete a service.
     *
     * @param serviceToDelete The service to be deleted as a Service object.
     * @return ResponseEntity with a success message if the service was successfully deleted.
     */
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

    /**
     * Handles the "/edit/service" endpoint and allows the vendor to edit a service.
     *
     * @param serviceToUpdate The updated service information as a Service object.
     * @return ResponseEntity with the updated service information.
     */
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

    /**
     * Handles the "/bookings" endpoint and retrieves the bookings associated with a user or vendor.
     *
     * @param jwtToken The JWT token obtained from the request header.
     * @return ResponseEntity with the list of bookings associated with the user or vendor.
     */
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

    /**
     * Handles the "/categories" endpoint and retrieves the list of categories.
     *
     * @return ResponseEntity with the list of available categories.
     */
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
