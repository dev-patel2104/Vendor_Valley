package com.group10.Controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
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
        log.info("request reservation payload - BookingController: {}", requestBookingModel.toString());
        try {
            if (bookingService.requestReservation(jwtToken, requestBookingModel)) {
                return ResponseEntity.ok("Booking request has been made");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot request the service now, Please try again");
            }
        } catch (SQLException | JWTVerificationException e) {
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
        log.info("respond to bookingRequest - BookingController: {}", bookingResponseRequestModel.toString());
        try {
            if (bookingService.respondToBooking(jwtToken, bookingResponseRequestModel)) {
                return ResponseEntity.ok("Booking status updated by vendor");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status of the service request");
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MailSendException | MailAuthenticationException | MailParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        } catch (NoInformationFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
