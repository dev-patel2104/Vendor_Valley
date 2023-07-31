package com.group10.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.EmailDetails;
import com.group10.Model.RequestBooking;
import com.group10.Repository.BookingRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import com.group10.Util.BookingUtil;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;
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
        if (jwtToken == null) return false;
        if (requestBookingModel == null) return false;
        if (requestBookingModel.getServiceID() == null) return false;
        if (requestBookingModel.getBookingDate() == null) return false;
        if (requestBookingModel.getStartDate() == null) return false;
        if (requestBookingModel.getEndDate() == null) return false;

        DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(jwtToken);
        int customerId = decodedJWT.getClaim("userId").asInt();

        try {
            if (bookingRepository.requestReservation(customerId, requestBookingModel)) {
                //send mail to vendor about the customer's request
                com.group10.Model.Service vendorService = serviceRepository.getServiceDetails(requestBookingModel.getServiceID());

                if (vendorService != null && vendorService.getServiceName() != null && vendorService.getCompanyEmail() != null) {
                    String subject = "VendorValley: A customer has requested for " + vendorService.getServiceName();
                    String body = "Respond to the customer's request on the vendor valley website !!"; // todo: add site url ?

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
        if (bookingResponseRequest == null) {
            throw new NoInformationFoundException("No booking information found to respond for the booking");
        }

        if (jwtToken == null) return false;
        if (bookingResponseRequest.getBookingID() == null) return false;
        if (bookingResponseRequest.getServiceID() == null) return false;
        if (bookingResponseRequest.getBookingStatus() == null) return false;
        if (!BookingUtil.isValidBookingStatus(bookingResponseRequest.getBookingStatus())) return false;

        DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(jwtToken);
        String customerEmail = decodedJWT.getClaim("email").asString();

        if (bookingRepository.respondToBooking(bookingResponseRequest)) {
            //send a mail to customer
            String subject = "VendorValley: You have received a response to your booking request";
            String body = "Request for the service: " + bookingResponseRequest.getServiceID() + " has been " + bookingResponseRequest.getBookingStatus();

            emailDetails.setRecipient(customerEmail);
            emailDetails.setSubject(subject);
            emailDetails.setMsgBody(body);

            emailUtil.sendSimpleMail(emailDetails);

            return true;
        }
        return false;
    }
}
