package com.group10.EnumTest;

import com.group10.Enums.BookingStatus;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookingStatusTest
{
    @Test
    public void testAwaitingStatus() {
        BookingStatus status = BookingStatus.AWAITING;
        assertEquals("awaiting", status.getBookingStatus());
    }

    @Test
    public void testAcceptedStatus() {
        BookingStatus status = BookingStatus.ACCEPTED;
        assertEquals("accepted", status.getBookingStatus());
    }

    @Test
    public void testDeclinedStatus() {
        BookingStatus status = BookingStatus.DECLINED;
        assertEquals("declined", status.getBookingStatus());
    }

    @Test
    public void testEnumValues() {
        BookingStatus[] values = BookingStatus.values();
        assertEquals(3, values.length);
        assertEquals(BookingStatus.AWAITING, values[0]);
        assertEquals(BookingStatus.ACCEPTED, values[1]);
        assertEquals(BookingStatus.DECLINED, values[2]);
    }

    @Test
    public void testValueOf() {
        BookingStatus awaitingStatus = BookingStatus.valueOf("AWAITING");
        assertEquals(BookingStatus.AWAITING, awaitingStatus);

        BookingStatus acceptedStatus = BookingStatus.valueOf("ACCEPTED");
        assertEquals(BookingStatus.ACCEPTED, acceptedStatus);

        BookingStatus declinedStatus = BookingStatus.valueOf("DECLINED");
        assertEquals(BookingStatus.DECLINED, declinedStatus);
    }
}
