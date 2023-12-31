package com.group10.Controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.RequestBooking;
import com.group10.Service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Controller class that handles incoming HTTP requests related to bookings.
 */
@RestController
@Slf4j
public class BookingController {

    @Autowired
    BookingService bookingService;

    /**
     * Handles the HTTP POST request for booking a service.
     *
     * @param jwtToken     The JSON Web Token (JWT) provided in the request header for authentication.
     * @param requestBookingModel The booking information provided in the request body.
     * @return A ResponseEntity containing the response message based on the booking request status.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/booking")
    public ResponseEntity<String> requestReservation(@RequestHeader String jwtToken, @RequestBody RequestBooking requestBookingModel) {
        log.debug("Request reservation payload: {}", requestBookingModel.toString());
        try {
            if (bookingService.requestReservation(jwtToken, requestBookingModel)) {
                log.info("Reservation request is successful: {}", requestBookingModel);
                return ResponseEntity.ok("Booking request has been made");
            } else {
                log.info("Reservation request failed: {}", requestBookingModel);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot request the service now, Please try again");
            }
        } catch (SQLException | JWTVerificationException e) {
            log.error("An exception has occurred during booking request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Handles the HTTP POST request for responding to a booking request.
     *
     * @param bookingResponseRequestModel The booking response information provided in the request body.
     * @return A ResponseEntity containing the response message based on the booking response status.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/booking/respond")
    public ResponseEntity<String> respondToBookingRequest(@RequestHeader String jwtToken, @RequestBody BookingResponseRequest bookingResponseRequestModel) {
        log.debug("respond to bookingRequest: {}", bookingResponseRequestModel.toString());
        try {
            if (bookingService.respondToBooking(jwtToken, bookingResponseRequestModel)) {
                log.info("Respond to booking is successful: {}", bookingResponseRequestModel);
                return ResponseEntity.ok("Booking status updated by vendor");
            } else {
                log.info("Respond to booking failed: {}", bookingResponseRequestModel);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status of the service request");
            }
        } catch (SQLException e) {
            log.error("A SQL exception has occurred during booking request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MailSendException | MailAuthenticationException | MailParseException e) {
            log.error("A MailException has occurred during booking request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        } catch (NoInformationFoundException e) {
            log.error("A NoInformationFoundException has occurred during booking request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
