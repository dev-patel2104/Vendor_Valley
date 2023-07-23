package com.group10.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.EmailDetails;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.UserRepository;
import com.group10.Service.Interfaces.ICustomerSelectsVendorService;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;

/**
 * Service class that handles the logic for when a customer selects a vendor.
 */
@org.springframework.stereotype.Service
public class CustomerSelectsVendorServiceImpl implements ICustomerSelectsVendorService{

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailDetails emailDetails;


    /**
     * Retrieves the reviews for a given service ID.
     *
     * @param serviceId The ID of the service to retrieve reviews for.
     * @return A list of Review objects associated with the service ID.
     * @throws NumberFormatException If the service ID is not a valid integer.
     * @throws Exception If there is an error retrieving the reviews.
     */
    @Override
    public List<Review> getReviews(String serviceId) throws Exception {
        try{

            int serviceIdInt = Integer.parseInt(serviceId);
            boolean exists = checkIfServiceExists(serviceIdInt);
            if (!exists){
                return new ArrayList<>();
            }
            /**
             * Retrieves the reviews for a given service.
             *
             * @param serviceIdInt The ID of the service to retrieve reviews for.
             * @return The reviews for the specified service.
             */
            return serviceRepository.getReviewsForService(serviceIdInt);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Not a valid service id");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Checks if a service with the given service ID exists in the service repository.
     *
     * @param serviceId The ID of the service to check for existence.
     * @return true if the service exists, false otherwise.
     * @throws SQLException if there is an error while checking for service existence.
     */
    private boolean checkIfServiceExists(int serviceId) throws SQLException {
        boolean exists = false;
        try{
            /**
             * Checks if a service with the given serviceId exists in the service repository.
             *
             * @param serviceId The ID of the service to check for existence.
             * @return true if the service exists, false otherwise.
             */
            exists = serviceRepository.checkIfServiceExists(serviceId);
        }
        catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
        return exists;
    }

    /**
     * Writes a review for a user with the provided JWT token.
     *
     * @param review The review to be written.
     * @param JWTToken The JWT token used for authentication.
     * @return true if the review was successfully written, false otherwise.
     * @throws SQLException if there is an error with the database connection.
     * @throws JWTVerificationException if there is an error with the JWT token verification.
     */
    @Override
    public boolean writeReviews(Review review, String JWTToken) throws SQLException, JWTVerificationException {
        boolean result = false;
        
        /**
         * Decodes a JWT token using the provided JWT token handler.
         *
         * @param JWTToken The JWT token to decode
         * @return The decoded JWT object
         */
        // Check if booking exists
        int bookingId = review.getBookingId();
        if (bookingId == 0){
            return false;
        }
        boolean bookingExists = serviceRepository.checkIfBookingExists(bookingId);
        if (!bookingExists){
            return false;
        }
        DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
        // Get user id from JWT Token
        int userId = jwt.getClaim("userId").asInt();
        // Set user id in review object
        review.setReviewerId(userId);
        // Create review dateTime
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        // Set review dateTime
        review.setReviewDate(formattedDateTime);
        /**
         * Writes the given review to the service repository.
         *
         * @param review The review to be written.
         * @return The result of the write operation.
         */
        result = serviceRepository.writeReviews(review);
        return result;
    }

    /**
     * Sends an email with the provided service ID, email text, and JWT token.
     *
     * @param serviceId The ID of the service.
     * @param emailText The text of the email.
     * @param JWTToken The JWT token for authentication.
     * @return True if the email was sent successfully, false otherwise.
     * @throws MailAuthenticationException If there is an authentication error while sending the email.
     * @throws MailSendException If there is an error while sending the email.
     * @throws MailParseException If there is an error parsing the email.
     * @throws SQLException If there is an error with the SQL database.
     * @throws JWTVerificationException If there is an error verifying the JWT token.
     */
    @Override
    public boolean sendEmail(String serviceId, String emailText, String JWTToken) throws MailAuthenticationException, MailSendException, MailParseException, SQLException, JWTVerificationException{
        if (serviceId == null || serviceId.equals("") || JWTToken == null || JWTToken.equals("") || emailText == null || emailText.equals(""))
        {
            return false;
        }
        boolean result = false;
        /**
         * Decodes a JWT token using the provided JWT token handler.
         *
         * @param JWTToken The JWT token to decode
         * @return The decoded JWT object
         */
        DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
        // Get user email from JWT Token
        String senderEmail = jwt.getClaim("email").asString(); 
        // Get user name from user email
        User user = userRepository.findByEmail(senderEmail);
        String name = user.getFullName();
        // Get service name from service id
        int serviceIdInt = Integer.parseInt(serviceId);
        Service service = serviceRepository.getServiceDetails(serviceIdInt);
        if (service == null || user == null){
            return false;
        }
        // Create email details object
        emailDetails.setSubject("You have a new message from " + name + " regarding your service " + service.getServiceName() + "!");
        emailDetails.setMsgBody(emailText +"\n\n" + "Sent by: " + senderEmail);
        emailDetails.setRecipient(service.getCompanyEmail());
        /**
         * Sends a simple email using the provided email details.
         *
         * @param emailDetails The details of the email to be sent
         * @return The result of the email sending operation
         */
        result = emailUtil.sendSimpleMail(emailDetails);
        if (!result){
            return false;
        }
        return true;
    }

}
