package com.group10.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
/**
 * Class representing a booking request for a service.
 */
public class RequestBooking {

    /**
     * The ID of the service for which the booking is requested.
     */
    private Integer serviceID;

    /**
     * The date on which the booking is requested.
     */
    private String bookingDate;

    /**
     * The start date of the booking.
     */
    private String startDate;

    /**
     * The end date of the booking.
     */
    private String endDate;

}