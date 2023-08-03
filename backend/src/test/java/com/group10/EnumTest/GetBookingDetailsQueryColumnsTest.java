package com.group10.EnumTest;

import com.group10.Enums.GetBookingDetailsQueryColumns;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBookingDetailsQueryColumnsTest
{
    @Test
    public void testServiceNameColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.SERVICE_NAME;
        assertEquals("service_name", column.getColumnName());
    }

    @Test
    public void testBookingIdColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.BOOKING_ID;
        assertEquals("booking_id", column.getColumnName());
    }

    @Test
    public void testBookingDateColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.BOOKING_DATE;
        assertEquals("booking_date", column.getColumnName());
    }

    @Test
    public void testStartDateColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.START_DATE;
        assertEquals("start_date", column.getColumnName());
    }

    @Test
    public void testEndDateColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.END_DATE;
        assertEquals("end_date", column.getColumnName());
    }

    @Test
    public void testBookingStatusColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.BOOKING_STATUS;
        assertEquals("booking_status", column.getColumnName());
    }

    @Test
    public void testServiceIdColumn() {
        GetBookingDetailsQueryColumns column = GetBookingDetailsQueryColumns.SERVICE_ID;
        assertEquals("service_id", column.getColumnName());
    }

    @Test
    public void testEnumValues() {
        GetBookingDetailsQueryColumns[] values = GetBookingDetailsQueryColumns.values();
        assertEquals(7, values.length);
        assertEquals(GetBookingDetailsQueryColumns.SERVICE_NAME, values[0]);
        assertEquals(GetBookingDetailsQueryColumns.BOOKING_ID, values[1]);
        assertEquals(GetBookingDetailsQueryColumns.BOOKING_DATE, values[2]);
        assertEquals(GetBookingDetailsQueryColumns.START_DATE, values[3]);
        assertEquals(GetBookingDetailsQueryColumns.END_DATE, values[4]);
        assertEquals(GetBookingDetailsQueryColumns.BOOKING_STATUS, values[5]);
        assertEquals(GetBookingDetailsQueryColumns.SERVICE_ID, values[6]);
    }

    @Test
    public void testValueOf() {
        GetBookingDetailsQueryColumns serviceNameColumn = GetBookingDetailsQueryColumns.valueOf("SERVICE_NAME");
        assertEquals(GetBookingDetailsQueryColumns.SERVICE_NAME, serviceNameColumn);

        GetBookingDetailsQueryColumns bookingIdColumn = GetBookingDetailsQueryColumns.valueOf("BOOKING_ID");
        assertEquals(GetBookingDetailsQueryColumns.BOOKING_ID, bookingIdColumn);

    }
}
