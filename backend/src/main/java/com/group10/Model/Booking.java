package com.group10.Model;

/**
 * Represents a booking entity with various properties.
 */
public class Booking {
    private int bookingId;
    private User user;
    private String serviceName;
    private String bookingDate;
    private String startDate;
    private String endDate;
    private String bookingStatus;
    private int serviceId;

    /**
     * Get the name of the service associated with this booking.
     *
     * @return The service name.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Set the name of the service associated with this booking.
     *
     * @param serviceName The service name to set.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    /**
     * Get the user associated with this booking.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user associated with this booking.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Get the booking ID.
     *
     * @return The booking ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Set the booking ID.
     *
     * @param bookingId The booking ID to set.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }


    /**
     * Get the booking date.
     *
     * @return The booking date.
     */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
     * Set the booking date.
     *
     * @param bookingDate The booking date to set.
     */
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }


    /**
     * Get the start date of the booking.
     *
     * @return The start date.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the booking.
     *
     * @param startDate The start date to set.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    /**
     * Get the end date of the booking.
     *
     * @return The end date.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set the end date of the booking.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    /**
     * Get the booking status.
     *
     * @return The booking status.
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Set the booking status.
     *
     * @param bookingStatus The booking status to set.
     */
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


    /**
     * Get the service ID associated with this booking.
     *
     * @return The service ID.
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Set the service ID associated with this booking.
     *
     * @param serviceId The service ID to set.
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * Generates a string representation of the Booking object.
     *
     * @return A string containing the details of the booking.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", serviceName='" + serviceName + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
