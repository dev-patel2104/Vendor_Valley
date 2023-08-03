package com.group10.UtilTests;

import com.group10.Util.BookingUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingUtilTest {

    @Test
    public void isValidBookingStatusTest() {
        assertTrue(BookingUtil.isValidBookingStatus("awaiting"));
        assertTrue(BookingUtil.isValidBookingStatus("accepted"));
        assertTrue(BookingUtil.isValidBookingStatus("declined"));
        assertFalse(BookingUtil.isValidBookingStatus("dngsjdg"));
        assertFalse(BookingUtil.isValidBookingStatus(null));
        assertFalse(BookingUtil.isValidBookingStatus(""));
        assertFalse(BookingUtil.isValidBookingStatus("     "));
    }
}
