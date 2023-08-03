package com.group10.Enums;

/**
 * Enum representing the status of a booking request.
 */
public enum BookingStatus {

    /**
     * The booking request is awaiting approval.
     */
    AWAITING("awaiting"),

    /**
     * The booking request has been accepted.
     */
    ACCEPTED("accepted"),

    /**
     * The booking request has been declined.
     */
    DECLINED("declined");

    private String bookingStatus;

    /**
     * Constructor for the BookingStatus enum.
     *
     * @param status The status string associated with the enum constant.
     */
    BookingStatus(String status) {
        this.bookingStatus = status;
    }

    /**
     * Get the status string associated with the enum constant.
     *
     * @return The status string.
     */
    public String getBookingStatus() {
        return bookingStatus;
    }
}
