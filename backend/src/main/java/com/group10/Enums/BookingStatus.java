package com.group10.Enums;

public enum BookingStatus {

    AWAITING("awaiting"),
    ACCEPTED("accepted"),
    DECLINED("declined");

    private String bookingStatus;

    BookingStatus(String status) {
        this.bookingStatus = status;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}
