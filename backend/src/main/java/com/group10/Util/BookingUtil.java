package com.group10.Util;

import com.group10.Enums.BookingStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for handling booking-related operations.
 */
@Slf4j
public class BookingUtil {

    /**
     * Checks if the given booking status is valid.
     *
     * @param bookingStatus The booking status string to check.
     * @return true if the booking status is valid, false otherwise.
     */
    public static boolean isValidBookingStatus(String bookingStatus) {
        if (bookingStatus == null) {
            log.warn("Booking status is null.");
            return false;
        }
        if (bookingStatus.isEmpty() || bookingStatus.isBlank()) {
            log.warn("Booking status is empty or blank.");
            return false;
        }

        for (BookingStatus status : BookingStatus.values()) {
            if (status.getBookingStatus().equalsIgnoreCase(bookingStatus)) {
                log.debug("Valid booking status: " + bookingStatus);
                return true;
            }
        }

        log.debug("Invalid booking status: " + bookingStatus);
        return false;
    }
}
