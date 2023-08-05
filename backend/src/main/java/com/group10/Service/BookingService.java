package com.group10.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.EmailDetails;
import com.group10.Model.RequestBooking;
import com.group10.Repository.BookingRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import com.group10.Util.BookingUtil;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;
import com.group10.Util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * This service class handles booking-related operations such as requesting reservations
 * and responding to booking requests.
 */
@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailDetails emailDetails;
    

    /**
     * Requests a reservation using the provided JWT token and booking information.
     *
     * @param jwtToken     The JSON Web Token (JWT) provided by the client for authentication.
     * @param requestBookingModel The booking information to be requested for reservation.
     * @return true if the reservation request is successful, false otherwise.
     * @throws SQLException           If there is an error executing the database query.
     * @throws JWTVerificationException If the JWT token cannot be verified.
     */
    public boolean requestReservation(String jwtToken, RequestBooking requestBookingModel) throws SQLException, JWTVerificationException {
        if (jwtToken == null) {
            log.warn("JWT token is null.");
            return false;
        }
        if (requestBookingModel == null) {
            log.warn("Request booking model is null.");
            return false;
        }
        if (requestBookingModel.getServiceID() == null) {
            log.warn("Service ID is null.");
            return false;
        }
        if (requestBookingModel.getBookingDate() == null) {
            log.warn("Booking date is null.");
            return false;
        }
        if (requestBookingModel.getStartDate() == null) {
            log.warn("Start date is null.");
            return false;
        }
        if (requestBookingModel.getEndDate() == null) {
            log.warn("End date is null.");
            return false;
        }

        DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(jwtToken);
        int customerId = decodedJWT.getClaim("userId").asInt();

        try {
            // Check if booking is in the past (i.e. start date is before booking date)
            if (StringUtil.dateStringToDate(requestBookingModel.getStartDate()).before(StringUtil.dateStringToDate(requestBookingModel.getBookingDate()))) {
                log.warn("Booking cannot be made in the past (start date)");
                throw new SQLException("Booking cannot be made in the past");
            }
            // Check if booking is in the past (i.e. end date is before booking date)
            if (StringUtil.dateStringToDate(requestBookingModel.getEndDate()).before(StringUtil.dateStringToDate(requestBookingModel.getBookingDate()))) {
                log.warn("Booking cannot be made in the past (end date)");
                throw new SQLException("Booking cannot be made in the past");
            }
            // Check if booking ends before it starts (i.e. end date is before start date)
            if (StringUtil.dateStringToDate(requestBookingModel.getEndDate()).before(StringUtil.dateStringToDate(requestBookingModel.getStartDate()))) {
                log.warn("Booking cannot end before it starts");
                throw new SQLException("Booking cannot end before it starts");
            }

            if (bookingRepository.requestReservation(customerId, requestBookingModel)) {
                //send mail to vendor about the customer's request
                com.group10.Model.Service vendorService = serviceRepository.getServiceDetails(requestBookingModel.getServiceID());

                if (vendorService != null && vendorService.getServiceName() != null && vendorService.getCompanyEmail() != null) {
                    String subject = "VendorValley: A customer has requested for " + vendorService.getServiceName();
                    String body = "Respond to the customer's request on the vendor valley website !!";

                    emailDetails.setRecipient(vendorService.getCompanyEmail());
                    emailDetails.setSubject(subject);
                    emailDetails.setMsgBody(body);

                    emailUtil.sendSimpleMail(emailDetails);
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while processing reservation request: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Responds to a booking request with the provided booking response information.
     *
     * @param bookingResponseRequest The booking response information to be processed.
     * @return true if the booking response is successful and the email notification is sent, false otherwise.
     * @throws SQLException               If there is an error executing the database query.
     * @throws MailAuthenticationException If there is an authentication issue while sending the email.
     * @throws MailSendException           If there is an issue while sending the email.
     * @throws MailParseException          If there is a parsing issue with the email.
     * @throws NoInformationFoundException If there is no information found to respond for the booking.
     */
    public boolean respondToBooking(String jwtToken, BookingResponseRequest bookingResponseRequest)
            throws SQLException, MailAuthenticationException, MailSendException, MailParseException, NoInformationFoundException {

        try {
            if (bookingResponseRequest == null) {
                log.warn("No booking information found to respond for the booking.");
                throw new NoInformationFoundException("No booking information found to respond for the booking");
            }

            if (jwtToken == null) {
                log.warn("JWT token is null.");
                return false;
            }
            if (bookingResponseRequest.getBookingID() == null) {
                log.warn("Booking ID is null.");
                return false;
            }
            if (bookingResponseRequest.getServiceID() == null) {
                log.warn("Service ID is null.");
                return false;
            }
            if (bookingResponseRequest.getBookingStatus() == null) {
                log.warn("Booking status is null.");
                return false;
            }
            if (!BookingUtil.isValidBookingStatus(bookingResponseRequest.getBookingStatus())) {
                log.warn("Invalid booking status: {}", bookingResponseRequest.getBookingStatus());
                return false;
            }

            // Check if booking has ended
            Booking booking = bookingRepository.hasBookingEnded(bookingResponseRequest.getBookingID());
            if (booking == null || booking.getUser() == null || booking.getUser().getEmail() == null) {
                log.warn("Booking has already ended.");
                throw new SQLException("Booking has already ended");
            }

            if (bookingRepository.respondToBooking(bookingResponseRequest)) {
                //send a mail to customer
                String subject = "VendorValley: You have received a response to your booking request";
                String body = "Request for the service: " + bookingResponseRequest.getServiceID() + " has been " + bookingResponseRequest.getBookingStatus();

                emailDetails.setRecipient(booking.getUser().getEmail());
                emailDetails.setSubject(subject);
                emailDetails.setMsgBody(body);

                emailUtil.sendSimpleMail(emailDetails);

                log.info("Booking response sent successfully.");
                return true;
            }

            log.warn("Booking response failed to send.");
            return false;
        } catch (SQLException e) {
            log.error("Error while responding to booking: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

}
