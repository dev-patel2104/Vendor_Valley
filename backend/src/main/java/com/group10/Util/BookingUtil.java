package com.group10.Util;

import com.group10.Enums.BookingStatus;

public class BookingUtil {

    public static boolean isValidBookingStatus(String bookingStatus) {
        for(BookingStatus status: BookingStatus.values()) {
            if (status.getBookingStatus().equals(bookingStatus)) return true;
        }
        return false;
    }

}
