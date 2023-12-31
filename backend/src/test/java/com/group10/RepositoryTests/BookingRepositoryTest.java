package com.group10.RepositoryTests;

import com.group10.Model.BookingResponseRequest;
import com.group10.Model.RequestBooking;
import com.group10.Repository.BookingRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingRepositoryTest {

    @Mock
    private IDatabaseService databaseService;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestReservation_Success() throws SQLException {
        // Arrange
        int customerId = 1;
        RequestBooking bookingModel = new RequestBooking();
        bookingModel.setServiceID(8);
        bookingModel.setBookingDate("2023-07-20");
        bookingModel.setStartDate("2023-07-21");
        bookingModel.setEndDate("2023-07-23");

        when(databaseService.connect()).thenReturn(connection);
        when(connection.prepareStatement(SQLQueries.insertBookingEntry)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = bookingRepository.requestReservation(customerId, bookingModel);

        // Assert
        assertTrue(result);
        verify(connection).close();
        verify(preparedStatement).close();
    }

    @Test
    void testRespondToBooking_Success() throws SQLException, MailAuthenticationException, MailSendException, MailParseException {
        // Arrange
        BookingResponseRequest bookingResponseRequest = new BookingResponseRequest();
        bookingResponseRequest.setBookingStatus("decline");
        bookingResponseRequest.setBookingID(123);
        bookingResponseRequest.setServiceID(23);

        when(databaseService.connect()).thenReturn(connection);
        when(connection.prepareStatement(SQLQueries.updateBookingEntry)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = bookingRepository.respondToBooking(bookingResponseRequest);

        // Assert
        assertTrue(result);
        verify(preparedStatement).close();
    }
}
