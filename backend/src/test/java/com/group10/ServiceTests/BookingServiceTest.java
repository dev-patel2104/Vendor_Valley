package com.group10.ServiceTests;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.EmailDetails;
import com.group10.Repository.BookingRepository;
import com.group10.Service.BookingService;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    JWTTokenHandler jwtTokenHandler;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private EmailDetails emailDetails;

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
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim claim = mock(Claim.class);
        String token = "jwt_token";
        Booking booking = new Booking();
        booking.setServiceName("Florist");
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
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim claim = mock(Claim.class);
        String token = "jwt_token";
        Booking booking = new Booking();
        booking.setServiceName("Florist");
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
    public void nullBookingResponseRequest_respondToBooking() throws NoInformationFoundException, SQLException {
        BookingResponseRequest bookingResponseRequest = null;

        BookingService newBookingService = mock(BookingService.class);
        when(newBookingService.respondToBooking(bookingResponseRequest)).thenThrow(new NoInformationFoundException("test"));
        assertThrows(NoInformationFoundException.class, () -> newBookingService.respondToBooking(bookingResponseRequest));
    }

    @Test
    public void repoSuccess_respondToBooking() throws SQLException, NoInformationFoundException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        bookingResponseRequest.setBookingStatus("accept");
        bookingResponseRequest.setBookingID(342);
        bookingResponseRequest.setServiceID(23);
        bookingResponseRequest.setCustomerEmail("test@mail.com");

        when(bookingRepository.respondToBooking(any(BookingResponseRequest.class))).thenReturn(true);

        when(emailUtil.sendSimpleMail(any(EmailDetails.class))).thenReturn(true);

        assertTrue(bookingService.respondToBooking(bookingResponseRequest));
    }

    @Test
    public void repoFail_respondToBooking() throws SQLException, NoInformationFoundException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        bookingResponseRequest.setBookingStatus("accept");
        bookingResponseRequest.setBookingID(342);
        bookingResponseRequest.setServiceID(23);
        bookingResponseRequest.setCustomerEmail("test@mail.com");

        when(bookingRepository.respondToBooking(any(BookingResponseRequest.class))).thenReturn(false);

        assertFalse(bookingService.respondToBooking(bookingResponseRequest));
    }

    @Test
    public void emptyAttrsRequestModel_respondToBooking() throws NoInformationFoundException, SQLException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        assertFalse(bookingService.respondToBooking(bookingResponseRequest));
    }
}
