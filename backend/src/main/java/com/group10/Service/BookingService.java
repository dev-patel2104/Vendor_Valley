package com.group10.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.EmailDetails;
import com.group10.Repository.BookingRepository;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JWTTokenHandler jwtTokenHandler;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private EmailDetails emailDetails;


    public boolean requestReservation(String jwtToken, Booking bookingModel) throws SQLException, JWTVerificationException {
        if (jwtToken == null) return false;
        if (bookingModel == null) return false;
        if (bookingModel.getServiceName() == null) return false;
        if (bookingModel.getBookingDate() == null) return false;
        if (bookingModel.getStartDate() == null) return false;
        if (bookingModel.getEndDate() == null) return false;
        if (bookingModel.getBookingStatus() == null) return false;

        DecodedJWT decodedJWT = jwtTokenHandler.decodeJWTToken(jwtToken);
        int customerId = decodedJWT.getClaim("userId").asInt();

        try {
            return bookingRepository.requestReservation(customerId, bookingModel);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean respondToBooking(BookingResponseRequest bookingResponseRequest) throws SQLException, MailAuthenticationException, MailSendException, MailParseException, NoInformationFoundException {
        if (bookingResponseRequest == null) {
            throw new NoInformationFoundException("No information found to respond for the booking");
        }
        if (bookingResponseRequest.getBookingStatus() == null) return false;
        if (bookingResponseRequest.getBookingID() == null) return false;
        if (bookingResponseRequest.getServiceName() == null) return false;
        if (bookingResponseRequest.getCustomerEmail() == null) return false;

        if (bookingRepository.respondToBooking(bookingResponseRequest)) {
            //send a mail to customer
            String subject = "VendorValley: You have received a response to your booking request";
            String body = "Request for " + bookingResponseRequest.getServiceName() + " " + bookingResponseRequest.getBookingStatus();

            emailDetails.setRecipient(bookingResponseRequest.getCustomerEmail());
            emailDetails.setSubject(subject);
            emailDetails.setMsgBody(body);

            emailUtil.sendSimpleMail(emailDetails);

            return true;
        }
        return false;
    }
}
