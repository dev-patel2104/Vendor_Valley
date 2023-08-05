package com.group10.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group10.Service.Interfaces.ICustomerSelectsVendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.group10.Model.Review;

/**
 * Controller class for handling customer selecting vendor service operations.
 */
@RestController
@Slf4j
public class CustomerSelectsVendorController {
    
    @Autowired
    private ICustomerSelectsVendorService customerSelectsVendorService;

    /**
     * Retrieves a list of reviews for a given service ID.
     *
     * @param body A map containing the request body, with the service ID as a key-value pair
     * @return A ResponseEntity object containing the list of reviews, or an appropriate error response
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/getReviews")
    public ResponseEntity<List<Review>> getReviews(@RequestBody Map<String, String> body)
    {
        log.debug("Received request to get reviews for serviceId: {}", body.get("serviceId"));

        String serviceId = body.get("serviceId");

        if (serviceId == null || serviceId.equals(""))
        {
            log.warn("Invalid or missing serviceId received. Returning BAD_REQUEST.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try
        {
            List<Review> reviews = new ArrayList<>();
            log.debug("Retrieving reviews for serviceId: {}", serviceId);

            /**
             * Retrieves the reviews for a specific service.
             *
             * @param serviceId The ID of the service to retrieve reviews for.
             * @return A list of reviews for the specified service.
             */
            reviews = customerSelectsVendorService.getReviews(serviceId);

            log.info("Retrieved {} reviews for serviceId: {}", reviews.size(), serviceId);

            return ResponseEntity.ok(reviews);
        }
        catch (SQLException e)
        {
            log.error("SQLException occurred while fetching reviews for serviceId {}: {}", serviceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        catch (NumberFormatException e){
            log.error("NumberFormatException occurred while parsing serviceId {}: {}", serviceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e){
            log.error("An unexpected exception occurred while fetching reviews for serviceId {}: {}", serviceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    /**
     * Endpoint for writing reviews.
     *
     * @param review The review object containing the review details.
     * @param JWTToken The JWT token for authentication.
     * @return ResponseEntity with a success message if the review is added successfully, or an error message if there is an issue.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/writeReviews")
    public ResponseEntity<String> writeReviews(@RequestBody Review review, @RequestHeader String JWTToken)
    {
        log.debug("Write Review payload: {}", review.toString());
        if (review == null || JWTToken == null || JWTToken.equals(""))
        {
            log.warn("Invalid or missing arguments received. Returning BAD_REQUEST.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        try
        {
            /**
             * Writes a review for a vendor service on behalf of a customer using a JWT token.
             *
             * @param review The review to be written.
             * @param JWTToken The JWT token representing the customer's authentication.
             * @return true if the review was successfully written, false otherwise.
             */
            boolean result = customerSelectsVendorService.writeReviews(review, JWTToken);

            if (result){
                log.info("Review added successfully using JWTToken");
                return ResponseEntity.ok("Review added successfully!");
            }
            else{
                log.error("Review could not be added using given JWTToken");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Review could not be added!");
            }
        }
        catch (Exception e){
            log.error("An unexpected exception occurred while writing review using JWTToken: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Sends an email using the provided information.
     *
     * @param body A map containing the necessary information for sending the email:
     *             - serviceId: The ID of the service.
     *             - emailText: The text of the email.
     *             - JWTToken: The JWT token.
     * @return A ResponseEntity with a status code and a message indicating the result of the email sending operation.
     *         - If the request is missing required arguments, it returns a BAD_REQUEST status with an "Invalid Arguments!" message.
     *         - If the email is sent successfully, it returns an OK status with an "Email sent successfully!" message.
     *         - If there is an error sending the email, it returns an INTERNAL_SERVER_ERROR status with
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail( @RequestHeader String JWTToken, @RequestBody Map<String, String> body)
    {
        log.debug("Received request to send email - .");

        String serviceId = body.get("serviceId");
        String emailText = body.get("emailText");

        if (serviceId == null || serviceId.equals("") || emailText == null || emailText.equals(""))
        {
            log.warn("Invalid or missing arguments received. Returning BAD_REQUEST.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments!");
        }
        try
        {
            log.debug("Sending email for serviceId: {}", serviceId);

            /**
             * Sends an email to the customer for the selected vendor service.
             *
             * @param serviceId The ID of the selected vendor service
             * @param emailText The text of the email to be sent
             * @param JWTToken The JWT token for authentication
             * @return true if the email was successfully sent, false otherwise
             */
            boolean result = customerSelectsVendorService.sendEmail(serviceId, emailText, JWTToken);

            if (!result){
                log.error("Email could not be sent for serviceId: {}", serviceId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email could not be sent!");
            }

            log.info("Email sent successfully for serviceId: {}", serviceId);
            return ResponseEntity.ok("Email sent successfully!");
        }
        catch (Exception e){
            log.error("An unexpected exception occurred while sending email for serviceId {}: {}", serviceId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
