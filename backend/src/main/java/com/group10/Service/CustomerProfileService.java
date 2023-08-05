package com.group10.Service;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Repository.Interfaces.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


/**
 * Service class for managing customer profiles.
 */
@Service
@Slf4j
public class CustomerProfileService extends ProfileService {

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Retrieves bookings for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of bookings associated with the user.
     * @throws SQLException If there is an error while accessing the database.
     * @throws UserDoesntExistException If the provided userId is negative.
     */
    @Override
    public List<Booking> getBookings(int userId) throws SQLException, UserDoesntExistException {
        try {
            if (userId < 0) {
                log.warn("No information for the given ID exists.");
                throw new UserDoesntExistException("No information for the given ID exists");
            }
            log.info("Retrieving bookings for user with ID: {}", userId);
            return customerRepository.getBookings(userId);
        } catch (SQLException e) {
            log.error("Error while retrieving bookings: {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}

