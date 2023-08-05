package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * Comparator for sorting services based on their total bookings.
 */
@Slf4j
public class BookingsComparator implements Comparator<Service> {
    /**
     * Compares two services based on their total bookings.
     *
     * @param s1 The first service to compare.
     * @param s2 The second service to compare.
     * @return A negative integer, zero, or a positive integer as the first service has fewer, equal to, or more bookings than the second service.
     */
    @Override
    public int compare(Service s1, Service s2) {
        int bookings1 = 0;
        int bookings2 = 0;
        try {
            bookings1 = Integer.parseInt(s1.getTotalBookings());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Failed to parse total bookings for service " + s1.getServiceName() + ": " + e.getMessage());
            bookings1 = 0;
        }
        try {
            bookings2 = Integer.parseInt(s2.getTotalBookings());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Failed to parse total bookings for service " + s2.getServiceName() + ": " + e.getMessage());
            bookings2 = 0;
        }
        int result = Integer.compare(bookings1, bookings2);
        log.debug("Comparing bookings for services " + s1.getServiceName() + " and " + s2.getServiceName() + ": " + result);
        return result;
    }
}