package com.group10.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.group10.Repository.Interfaces.ICustomerRepository;
import com.group10.Repository.Interfaces.IReviewsRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import lombok.extern.slf4j.Slf4j;
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
import com.group10.Service.Interfaces.ICustomerSelectsVendorService;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;

/**
 * Service class that handles the logic for when a customer selects a vendor.
 */
@org.springframework.stereotype.Service
@Slf4j
public class CustomerSelectsVendorServiceImpl implements ICustomerSelectsVendorService{

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private ICustomerRepository CustomerRepositoryImpl;

    @Autowired
    private IReviewsRepository serviceReviewsRepository;

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
    public List<Review> getReviews(String serviceId) throws NumberFormatException, Exception {
        try {
            int serviceIdInt = Integer.parseInt(serviceId);
            boolean exists = checkIfServiceExists(serviceIdInt);
            if (!exists) {
                log.info("No reviews found for service with ID: {}", serviceIdInt);
                return new ArrayList<>();
            }

            log.info("Retrieving reviews for service with ID: {}", serviceIdInt);
            return serviceReviewsRepository.getReviewsForService(serviceIdInt);
        } catch (NumberFormatException e) {
            log.error("Invalid service ID format: {}", serviceId);
            throw new NumberFormatException("Not a valid service ID");
        } catch (Exception e) {
            log.error("Error while retrieving reviews: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
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
        try {
            // Check if booking exists
            int bookingId = review.getBookingId();
            if (bookingId == 0) {
                log.warn("Invalid booking ID in review: {}", bookingId);
                return false;
            }

            // Decode JWT token
            DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
            int userId = jwt.getClaim("userId").asInt();
            review.setReviewerId(userId);

            // Check if booking exists and matches user
            boolean bookingExists = serviceRepository.checkIfBookingExists(bookingId, review.getServiceId(), review.getReviewerId());
            if (!bookingExists) {
                log.warn("Booking does not exist or does not match user: bookingId={}, serviceId={}, reviewerId={}",
                        bookingId, review.getServiceId(), review.getReviewerId());
                return false;
            }
            review.setBookingId(bookingId);

            log.info("Writing review for serviceId={}, reviewerId={}", review.getServiceId(), review.getReviewerId());
            return startWritingReview(review);
        } catch (JWTVerificationException e) {
            log.error("JWT Token verification failed: {}", e.getMessage());
            throw e;
        } catch (SQLException e) {
            log.error("Error while writing review: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
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
    public boolean sendEmail(String serviceId, String emailText, String JWTToken)
            throws MailAuthenticationException, MailSendException, MailParseException, SQLException, JWTVerificationException {
        if (serviceId == null || serviceId.equals("") || JWTToken == null || JWTToken.equals("") || emailText == null || emailText.equals("")) {
            return false;
        }

        try {
            DecodedJWT jwt = jwtTokenHandler.decodeJWTToken(JWTToken);
            String senderEmail = jwt.getClaim("email").asString();
            User user = CustomerRepositoryImpl.findByEmail(senderEmail);
            String name = user.getFullName();
            int serviceIdInt = Integer.parseInt(serviceId);
            Service service = serviceRepository.getServiceDetails(serviceIdInt);
            if (service == null || user == null) {
                return false;
            }

            emailDetails.setSubject("You have a new message from " + name + " regarding your service " + service.getServiceName() + "!");
            emailDetails.setMsgBody(emailText + "\n\n" + "Sent by: " + senderEmail);
            emailDetails.setRecipient(service.getCompanyEmail());

            log.info("Sending email from {} to {} regarding service {}", senderEmail, service.getCompanyEmail(), serviceId);

            boolean result = emailUtil.sendSimpleMail(emailDetails);

            if (result) {
                log.info("Email sent successfully.");
            } else {
                log.error("Failed to send email.");
            }

            return result;
        } catch (SQLException e) {
            log.error("Error while sending email: {}", e.getMessage());
            throw new SQLException("Error while sending email: " + e.getMessage());
        }
    }


    /**
     * Checks if a service with the given serviceId exists in the service repository.
     *
     * @param serviceId The ID of the service to check for existence.
     * @return true if the service exists, false otherwise.
     * @throws SQLException If an error occurs while checking service existence.
     */
    private boolean checkIfServiceExists(int serviceId) throws SQLException {
        boolean exists = false;
        try {
            log.info("Checking if service with ID {} exists", serviceId);
            exists = serviceRepository.checkIfServiceExists(serviceId);
            log.info("Service with ID {} exists: {}", serviceId, exists);
        } catch (SQLException e) {
            log.error("Error while checking service existence: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return exists;
    }


    /**
     * Writes the given review to the service repository.
     *
     * @param review The review to be written.
     * @return true if the review is written successfully, false otherwise.
     * @throws SQLException If an error occurs while writing the review.
     */
    private boolean startWritingReview(Review review) throws SQLException {
        try {
            // Create review dateTime
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            // Set review dateTime
            review.setReviewDate(formattedDateTime);

            log.info("Writing review for booking {} and service {}", review.getBookingId(), review.getServiceId());

            /**
             * Writes the given review to the service repository.
             *
             * @param review The review to be written.
             * @return The result of the write operation.
             */
            boolean result = serviceReviewsRepository.writeReviews(review);

            if (result) {
                log.info("Review successfully written.");
            } else {
                log.error("Failed to write review.");
            }

            return result;
        } catch (SQLException e) {
            log.error("Error while writing review: {}", e.getMessage());
            throw new SQLException("Error while writing review: " + e.getMessage());
        }
    }
}
