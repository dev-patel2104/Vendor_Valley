package com.group10.Repository;

import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class BookingRepository {

    @Autowired
    DatabaseService databaseService;

    public boolean requestReservation(int customerId, Booking bookingModel) throws SQLException {
        try (Connection connection = databaseService.connect();
        PreparedStatement requestReservationPreparedStatement = connection.prepareStatement(SQLQueries.insertBookingEntry)) {

            requestReservationPreparedStatement.setInt(1, customerId);
            requestReservationPreparedStatement.setString(2, bookingModel.getServiceName());
            requestReservationPreparedStatement.setString(3, bookingModel.getBookingDate());
            requestReservationPreparedStatement.setString(4, bookingModel.getStartDate());
            requestReservationPreparedStatement.setString(5, bookingModel.getEndDate());
            requestReservationPreparedStatement.setString(6, bookingModel.getBookingStatus());

            int rowsEffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsEffected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean respondToBooking(BookingResponseRequest bookingResponseRequest) throws SQLException, MailAuthenticationException, MailSendException, MailParseException {

        try (Connection connection = databaseService.connect();
             PreparedStatement requestReservationPreparedStatement = connection.prepareStatement(SQLQueries.updateBookingEntry)) {

            requestReservationPreparedStatement.setInt(1, bookingResponseRequest.getBookingStatus());
            requestReservationPreparedStatement.setInt(2, bookingResponseRequest.getBookingID());

            int rowsEffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsEffected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
