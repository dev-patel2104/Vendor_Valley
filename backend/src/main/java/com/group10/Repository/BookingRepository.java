package com.group10.Repository;

import com.group10.Enums.BookingStatus;
import com.group10.Model.Booking;
import com.group10.Model.BookingResponseRequest;
import com.group10.Model.RequestBooking;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Util.MapResultSetUtil;
import com.group10.Util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This repository class handles database operations related to bookings.
 */
@Repository
@Slf4j
public class BookingRepository {

    @Autowired
    IDatabaseService databaseService;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Requests a reservation by inserting booking information into the database.
     *
     * @param customerId   The ID of the customer making the reservation.
     * @param requestBookingModel The booking information to be inserted into the database.
     * @return true if the reservation request is successful and the data is inserted into the database, false otherwise.
     * @throws SQLException If there is an error executing the database query.
     */
    public boolean requestReservation(int customerId, RequestBooking requestBookingModel) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement requestReservationPreparedStatement = connection.prepareStatement(SQLQueries.insertBookingEntry)) {

            log.info("Requesting reservation for customer {} and service {}", customerId, requestBookingModel.getServiceID());

            requestReservationPreparedStatement.setInt(1, customerId);
            requestReservationPreparedStatement.setInt(2, requestBookingModel.getServiceID());
            requestReservationPreparedStatement.setDate(3, StringUtil.dateStringToDate(requestBookingModel.getBookingDate()));
            requestReservationPreparedStatement.setDate(4, StringUtil.dateStringToDate(requestBookingModel.getStartDate()));
            requestReservationPreparedStatement.setDate(5, StringUtil.dateStringToDate(requestBookingModel.getEndDate()));
            requestReservationPreparedStatement.setString(6, BookingStatus.AWAITING.getBookingStatus());

            int rowsAffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                log.info("Reservation requested successfully");
                return true;
            } else {
                log.warn("Failed to request reservation");
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while requesting reservation: {}", e.getMessage());
            throw new SQLException("Error while requesting reservation", e);
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

            log.info("Responding to booking request with ID: {}", bookingResponseRequest.getBookingID());

            requestReservationPreparedStatement.setString(1, bookingResponseRequest.getBookingStatus());
            requestReservationPreparedStatement.setInt(2, bookingResponseRequest.getBookingID());

            int rowsAffected = requestReservationPreparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                log.info("Booking response recorded successfully");
                return true;
            } else {
                log.warn("Failed to record booking response");
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while responding to booking: {}", e.getMessage());
            throw new SQLException("Error while responding to booking", e);
        }
    }


    /**
     * Checks if a booking has ended based on the provided booking ID.
     *
     * @param bookingId The ID of the booking to check.
     * @return The Booking object if it has ended, or null if it hasn't.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public Booking hasBookingEnded(int bookingId) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.getBoookingByBookingId)) {
            preparedStatement.setInt(1, bookingId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Booking booking = mapResultSetUtilObj.mapResultSetToHasBookingEnded(resultSet);

                if (booking == null) {
                    log.warn("Booking with ID {} does not exist", bookingId);
                    throw new SQLException("Booking does not exist");
                }

                if (StringUtil.dateStringToDate(booking.getEndDate()).before(new java.util.Date())) {
                    log.info("Booking with ID {} has ended", bookingId);
                    return booking;
                }
            }
        } catch (SQLException e) {
            log.error("Error while checking if booking has ended: {}", e.getMessage());
            throw new SQLException("Error while checking if booking has ended", e);
        }
        return null;
    }
}
