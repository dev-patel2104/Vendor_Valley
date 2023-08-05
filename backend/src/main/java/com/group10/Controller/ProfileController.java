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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public ResponseEntity<SignUpModel> getProfile(@RequestHeader String jwtToken) {
        log.info("Handling /profile endpoint");

        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        SignUpModel user;
        try {
            user = userProfileService.getProfile(token.getClaim("userId").asInt());
            log.info("Profile retrieved successfully for user with ID: {}", token.getClaim("userId").asInt());
        } catch (SQLException e) {
            log.error("Internal server error while retrieving profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (UserDoesntExistException e) {
            log.warn("User does not exist for the provided user ID: {}", token.getClaim("userId").asInt());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        log.info("Profile retrieved successfully for user with ID: {}", token.getClaim("userId").asInt());
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
    public ResponseEntity<String> editProfile(@RequestBody SignUpModel newInfo) {
        log.info("Handling /edit/profile endpoint with: {}", newInfo);

        try {
            if (newInfo == null) {
                log.warn("Received null newInfo in request");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            if (!userProfileService.editProfile(newInfo)) {
                log.warn("Failed to edit user's profile");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            log.info("Successfully edited the user's profile information");
        } catch (SQLException e) {
            log.error("Internal server error while editing profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NoInformationFoundException e) {
            log.warn("No information found for editing the profile", e);
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
    public ResponseEntity<SignUpModel> editCompanyDetails(@RequestBody SignUpModel updatedDetails) {
        log.info("Handling /edit/company endpoint: {}", updatedDetails);

        SignUpModel changedDetails;
        try {
            if (updatedDetails == null) {
                log.warn("Received null updatedDetails in request");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            changedDetails = vendorProfileService.editCompanyDetails(updatedDetails);
            if (changedDetails == null) {
                log.warn("Failed to edit company details");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            log.info("Successfully edited company details");
        } catch (SQLException e) {
            log.error("Internal server error while editing company details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NoInformationFoundException e) {
            log.warn("No information found for editing company details", e);
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
    public ResponseEntity<Service> addService(@RequestBody Service service) {
        log.info("Handling /addService endpoint: {}", service);

        Service addedService;
        try {
            if (service == null) {
                log.warn("Received null service in request");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            addedService = vendorProfileService.addService(service, categories);
            if (addedService == null) {
                log.warn("Failed to add service");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            log.info("Successfully added a new service");
        } catch (SQLException e) {
            log.error("Internal server error while adding a new service", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NoInformationFoundException e) {
            log.warn("No information found for adding a new service", e);
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
    public ResponseEntity<List<Service>> getServices(@RequestHeader String jwtToken) {
        log.info("Handling /services endpoint");

        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        List<Service> serviceList = new ArrayList<>();

        try {
            serviceList = vendorProfileService.getServices(token.getClaim("userId").asInt());
            log.info("Retrieved services for vendor with ID: {}", token.getClaim("userId").asInt());
        } catch (SQLException e) {
            log.error("Internal server error while retrieving services", e);
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (UserDoesntExistException e) {
            log.warn("User does not exist for the provided user ID: {}", token.getClaim("userId").asInt());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Service unavailable", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

        log.info("Successfully retrieved services for vendor with ID: {}", token.getClaim("userId").asInt());
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
    public ResponseEntity<String> deleteService(@RequestBody Service serviceToDelete) {
        log.info("Handling /delete/service endpoint: {}", serviceToDelete);

        try {
            if (serviceToDelete == null) {
                log.warn("Received null serviceToDelete in request");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            if (!vendorProfileService.deleteService(serviceToDelete)) {
                log.warn("Failed to delete service");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            log.info("Successfully deleted service");
        } catch (SQLException e) {
            log.error("Internal server error while deleting service", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NoInformationFoundException e) {
            log.warn("No information found for deleting service", e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        log.info("Service has been successfully deleted");
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
    public ResponseEntity<Service> editService(@RequestBody Service serviceToUpdate) {
        log.info("Handling /edit/service endpoint: {}", serviceToUpdate);

        Service updatedService;
        try {
            if (serviceToUpdate == null) {
                log.warn("Received null serviceToUpdate in request");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

            updatedService = vendorProfileService.editService(serviceToUpdate, categories);
            if (updatedService == null) {
                log.warn("Failed to edit service");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            log.info("Successfully edited service");
        } catch (SQLException e) {
            log.error("Internal server error while editing service", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (NoInformationFoundException e) {
            log.warn("No information found for editing service", e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }

        log.info("Service has been successfully edited");
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
    public ResponseEntity<List<Booking>> getBookings(@RequestHeader String jwtToken) {
        log.info("Received request to get bookings");

        DecodedJWT token = jwtTokenHandler.decodeJWTToken(jwtToken);
        List<Booking> bookingList = new ArrayList<>();
        int isVendor;

        try {
            isVendor = token.getClaim("isVendor").asInt();
            log.debug("isVendor claim in token: {}", isVendor);

            if (isVendor == 1) {
                log.debug("User is a vendor. Retrieving bookings for vendor.");
                bookingList = vendorProfileService.getBookings(token.getClaim("userId").asInt());
            } else {
                log.debug("User is not a vendor. Retrieving bookings for non-vendor user.");
                bookingList = userProfileService.getBookings(token.getClaim("userId").asInt());
            }
        } catch (SQLException e) {
            log.error("SQLException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (UserDoesntExistException e) {
            log.warn("UserDoesntExistException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Exception occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

        log.info("Request successfully processed. Returning booking list.");
        return ResponseEntity.ok(bookingList);
    }

    /**
     * Handles the "/categories" endpoint and retrieves the list of categories.
     *
     * @return ResponseEntity with the list of available categories.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        log.info("Received request to get categories");

        List<Category> categories;

        try {
            categories = vendorProfileService.getCategories();
            log.debug("Retrieved categories: {}", categories);
        } catch (SQLException e) {
            log.error("SQLException occurred while processing request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        log.info("Request successfully processed. Returning category list.");
        return ResponseEntity.ok(categories);
    }
}
