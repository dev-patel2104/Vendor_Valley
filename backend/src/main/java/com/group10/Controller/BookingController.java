package com.group10.Controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/booking")
    public ResponseEntity<String> requestReservation(@RequestHeader String jwtToken, @RequestBody Booking bookingModel) {
        try {
            if (bookingService.requestReservation(jwtToken, bookingModel)) {
                return ResponseEntity.ok("Booking request has been made");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot request the service now, Please try again");
            }
        } catch (SQLException | JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/booking/respond")
    public ResponseEntity<String> respondToBookingRequest(@RequestBody BookingResponseRequest bookingResponseRequestModel) {
        try {
            if (bookingService.respondToBooking(bookingResponseRequestModel)) {
                return ResponseEntity.ok("Booking status updated by vendor");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status of the service request");
            }
        } catch(SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        catch(MailSendException | MailAuthenticationException | MailParseException e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        } catch (NoInformationFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
