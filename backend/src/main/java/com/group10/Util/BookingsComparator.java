package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;

public class BookingsComparator implements Comparator<Service> {
    @Override
    public int compare(Service s1, Service s2) {
        int bookings1 = 0;
        int bookings2 = 0;
        try {
            bookings1 = Integer.parseInt(s1.getTotalBookings());
        } catch (NumberFormatException | NullPointerException  e) {
            bookings1 = 0;
        }
        try {
            bookings2 = Integer.parseInt(s2.getTotalBookings());
        } catch (NumberFormatException | NullPointerException  e) {
            bookings2 = 0;
        }
        return Integer.compare(bookings1, bookings2);
    }
}