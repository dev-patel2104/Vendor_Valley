package com.group10.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestBooking {
    private Integer serviceID;
    private String bookingDate;
    private String startDate;
    private String endDate;
}
