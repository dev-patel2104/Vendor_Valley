package com.group10.Model;

/**
 * Represents a request for booking response information.
 */
public class BookingResponseRequest {

    private Integer bookingID;
    private Integer serviceID;
    private String bookingStatus;

    /**
     * Retrieves the service ID associated with the booking response request.
     *
     * @return The service ID.
     */
    public Integer getServiceID() {
        return serviceID;
    }

    /**
     * Sets the service ID for the booking response request.
     *
     * @param serviceID The service ID to set.
     */
    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Retrieves the booking ID associated with the booking response request.
     *
     * @return The booking ID.
     */
    public Integer getBookingID() {
        return bookingID;
    }

    /**
     * Sets the booking ID for the booking response request.
     *
     * @param bookingID The booking ID to set.
     */
    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * Retrieves the booking status associated with the booking response request.
     *
     * @return The booking status.
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the booking status for the booking response request.
     *
     * @param bookingStatus The booking status to set.
     */
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * Generates a string representation of the BookingResponseRequest object.
     *
     * @return A string containing the booking ID, service ID, and booking status.
     */
    @Override
    public String toString() {
        return "BookingResponseRequest{" +
                "bookingID=" + bookingID +
                ", serviceID=" + serviceID +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
