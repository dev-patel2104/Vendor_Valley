package com.group10.Repository;

import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This repository class handles database operations related to bookings.
 */
@Repository
public class BookingRepository {

    @Autowired
    IDatabaseService databaseService;

    /**
     * Requests a reservation by inserting booking information into the database.
     *
     * @param customerId   The ID of the customer making the reservation.
     * @param bookingModel The booking information to be inserted into the database.
     * @return true if the reservation request is successful and the data is inserted into the database, false otherwise.
     * @throws SQLException If there is an error executing the database query.
     */
    public boolean requestReservation(int customerId, Booking bookingModel) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement requestReservationPreparedStatement = connection.prepareStatement(SQLQueries.insertBookingEntry)) {

            requestReservationPreparedStatement.setInt(1, customerId);
            requestReservationPreparedStatement.setDate(2, StringUtil.dateStringToDate(bookingModel.getBookingDate()));
            requestReservationPreparedStatement.setDate(3, StringUtil.dateStringToDate(bookingModel.getStartDate()));
            requestReservationPreparedStatement.setDate(4, StringUtil.dateStringToDate(bookingModel.getEndDate()));
            requestReservationPreparedStatement.setString(5, bookingModel.getBookingStatus());
            requestReservationPreparedStatement.setString(6, bookingModel.getServiceName());

            int rowsAffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Responds to a booking request by updating the booking status in the database.
     *
     * @param bookingResponseRequest The booking response information to be processed.
     * @return true if the booking response is successful and the booking status is updated in the database, false otherwise.
     * @throws SQLException               If there is an error executing the database query.
     * @throws MailAuthenticationException If there is an authentication issue while sending the email.
     * @throws MailSendException           If there is an issue while sending the email.
     * @throws MailParseException          If there is a parsing issue with the email.
     */
    public boolean respondToBooking(BookingResponseRequest bookingResponseRequest)
            throws SQLException, MailAuthenticationException, MailSendException, MailParseException {

        try (Connection connection = databaseService.connect();
             PreparedStatement requestReservationPreparedStatement = connection.prepareStatement(SQLQueries.updateBookingEntry)) {

            requestReservationPreparedStatement.setString(1, bookingResponseRequest.getBookingStatus());
            requestReservationPreparedStatement.setInt(2, bookingResponseRequest.getBookingID());

            int rowsAffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}