package com.group10.Util;

import com.group10.Enums.BookingStatus;

/**
 * Utility class for handling booking-related operations.
 */
public class BookingUtil {

    /**
     * Checks if the given booking status is valid.
     *
     * @param bookingStatus The booking status string to check.
     * @return true if the booking status is valid, false otherwise.
     */
    public static boolean isValidBookingStatus(String bookingStatus) {
        if (bookingStatus == null) return false;
        if (bookingStatus.isEmpty() || bookingStatus.isBlank()) return false;

        for (BookingStatus status : BookingStatus.values()) {
            if (status.getBookingStatus().equalsIgnoreCase(bookingStatus)) {
                return true;
            }
        }
        return false;
    }
}
