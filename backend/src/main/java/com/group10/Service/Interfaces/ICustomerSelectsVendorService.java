package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.group10.Model.Review;

/**
 * This interface defines the methods for a service that handles customer interactions with vendors.
 */
public interface ICustomerSelectsVendorService {
    /**
     * Retrieves a list of reviews for a given service ID.
     *
     * @param serviceId The ID of the service for which to retrieve reviews.
     * @return A list of Review objects representing the reviews for the service.
     * @throws Exception If an error occurs while retrieving the reviews.
     */
    public List<Review> getReviews(String serviceId) throws Exception;
    /**
     * Writes a review using the provided review object and JWT token.
     *
     * @param review The review object containing the review details.
     * @param JWTToken The JWT token for authentication.
     * @return True if the review was successfully written, false otherwise.
     * @throws Exception if an error occurs during the writing process.
     */
    public boolean writeReviews(Review review, String JWTToken) throws Exception;
    /**
     * Sends an email using the specified service ID, email text, and JWT token.
     *
     * @param serviceId The ID of the email service to use for sending the email.
     * @param emailText The text of the email to be sent.
     * @param JWTToken The JWT token for authentication.
     * @throws MailAuthenticationException If there is an authentication error while sending the email.
     * @throws MailSendException If there is an error while sending the email.
     * @throws MailParseException If there is an error parsing the email.
     * @throws SQLException If there is an error with the database connection.
     * @return true if the email was sent successfully, false otherwise.
     */
    public boolean sendEmail(String serviceId, String emailText, String JWTToken) throws MailAuthenticationException, MailSendException, MailParseException, SQLException;
}
