package com.group10.ServiceTests;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Repository.BookingRepository;
import com.group10.Service.BookingService;
import com.group10.Util.JWTTokenHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    JWTTokenHandler jwtTokenHandler;

    @InjectMocks
    BookingService bookingService;

    @Test
    public void nullJWTtoken_requestReservation() throws SQLException {
        assertFalse(bookingService.requestReservation(null, new Booking()));
    }

    @Test
    public void nullBookingModel_requestReservation() throws SQLException {
        assertFalse(bookingService.requestReservation("asdfdsgdjfgndf+gsdnfgsd", null));
    }

    @Test
    public void repoFailed_requestReservation() throws SQLException {
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        String token = "jwt_token";
        Booking booking = new Booking();
        booking.setServiceName("florist");
        booking.setBookingDate("07-12-2023");
        booking.setStartDate("07-12-2023");
        booking.setEndDate("07-12-2023");
        booking.setBookingStatus("decline");

        int userID = 616;

        Mockito.doReturn(userID).when(claim).asInt();
        Mockito.doReturn(claim).when(decodedJWT).getClaim("userId");
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);

        Mockito.doReturn(false).when(bookingRepository).requestReservation(userID, booking);

        assertFalse(bookingService.requestReservation(token, booking));
    }

    @Test
    public void repoSuccess_requestReservation() throws SQLException {
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        String token = "jwt_token";
        Booking booking = new Booking();
        booking.setServiceName("florist");
        booking.setBookingDate("07-12-2023");
        booking.setStartDate("07-12-2023");
        booking.setEndDate("07-12-2023");
        booking.setBookingStatus("decline");
        int userID = 616;

        Mockito.doReturn(userID).when(claim).asInt();
        Mockito.doReturn(claim).when(decodedJWT).getClaim("userId");
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);

        Mockito.doReturn(true).when(bookingRepository).requestReservation(userID, booking);

        assertTrue(bookingService.requestReservation(token, booking));
    }

    @Test
    public void emptyAttrsRequestModel_respondToBooking() throws NoInformationFoundException, SQLException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        assertFalse(bookingService.respondToBooking(bookingResponseRequest));
    }
}
