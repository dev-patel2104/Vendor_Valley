package com.group10.Model;

public class BookingResponseRequest {

    private Integer bookingID;
    private Integer serviceID;
    private String customerEmail;
    private String bookingStatus;

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "BookingResponseRequest{" +
                "bookingID=" + bookingID +
                ", serviceName='" + serviceID + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
