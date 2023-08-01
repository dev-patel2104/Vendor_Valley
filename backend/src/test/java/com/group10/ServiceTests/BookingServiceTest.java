package com.group10.ServiceTests;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.*;
import com.group10.Repository.BookingRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    IServiceRepository serviceRepository;

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
        assertFalse(bookingService.requestReservation(null, new RequestBooking()));
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
        RequestBooking requestBooking = new RequestBooking();
        requestBooking.setServiceID(666);
        requestBooking.setBookingDate("07-12-2023");
        requestBooking.setStartDate("07-12-2023");
        requestBooking.setEndDate("07-12-2023");

        int userID = 616;

        Mockito.doReturn(userID).when(claim).asInt();
        Mockito.doReturn(claim).when(decodedJWT).getClaim("userId");
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);

        Mockito.doReturn(false).when(bookingRepository).requestReservation(userID, requestBooking);
        assertFalse(bookingService.requestReservation(token, requestBooking));
    }

    @Test
    public void repoSuccess_requestReservation() throws SQLException {
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim claim = mock(Claim.class);
        String token = "jwt_token";
        RequestBooking requestBooking = new RequestBooking();
        requestBooking.setServiceID(6);
        requestBooking.setBookingDate("07-12-2023");
        requestBooking.setStartDate("07-12-2023");
        requestBooking.setEndDate("07-12-2023");
        int userID = 616;

        Mockito.doReturn(userID).when(claim).asInt();
        Mockito.doReturn(claim).when(decodedJWT).getClaim("userId");
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(true).when(bookingRepository).requestReservation(userID, requestBooking);

        Service someVendorService = new Service();
        someVendorService.setCompanyEmail("test@boon.ca");
        Mockito.doReturn(someVendorService).when(serviceRepository).getServiceDetails(Mockito.anyInt());

        assertTrue(bookingService.requestReservation(token, requestBooking));
    }

    @Test
    public void nullBookingResponseRequest_respondToBooking() throws NoInformationFoundException, SQLException {
        BookingResponseRequest bookingResponseRequest = null;

        BookingService newBookingService = mock(BookingService.class);
        when(newBookingService.respondToBooking("jwt token", bookingResponseRequest)).thenThrow(new NoInformationFoundException("test"));
        assertThrows(NoInformationFoundException.class, () -> newBookingService.respondToBooking("jwt token", bookingResponseRequest));
    }

    @Test
    public void repoSuccess_respondToBooking() throws SQLException, NoInformationFoundException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        bookingResponseRequest.setBookingStatus("accepted");
        bookingResponseRequest.setBookingID(342);
        bookingResponseRequest.setServiceID(23);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Claim claim = mock(Claim.class);

        String token = "jwt_token";
        String email = "boon@dal.ca";
        int id = 5;
        Booking booking = new Booking();
        User user = new User();
        user.setEmail(email);
        booking.setUser(user);

        Mockito.doReturn(booking).when(bookingRepository).hasBookingEnded(anyInt());
        Mockito.doReturn(true).when(bookingRepository).respondToBooking(any(BookingResponseRequest.class));
        //when(bookingRepository.respondToBooking(any(BookingResponseRequest.class))).thenReturn(true);
        Mockito.doReturn(true).when(emailUtil).sendSimpleMail(any(EmailDetails.class));
        //when(emailUtil.sendSimpleMail(any(EmailDetails.class))).thenReturn(true);

        assertTrue(bookingService.respondToBooking(token, bookingResponseRequest));
    }

    @Test
    public void repoFail_respondToBooking() throws SQLException, NoInformationFoundException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        bookingResponseRequest.setBookingStatus("invalid booking status");
        bookingResponseRequest.setBookingID(342);
        bookingResponseRequest.setServiceID(23);

        assertFalse(bookingService.respondToBooking("jwt token", bookingResponseRequest));
    }

    @Test
    public void emptyAttrsRequestModel_respondToBooking() throws NoInformationFoundException, SQLException {
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        assertFalse(bookingService.respondToBooking("jwt token", bookingResponseRequest));
    }
}
