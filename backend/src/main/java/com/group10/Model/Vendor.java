package com.group10.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vendor {
    private int userId;
    private String userRole;
    private String companyName;
    private String companyEmail;
    private String companyRegistrationID;
    private String companyMobile;
    private String companyStreet;
    private String companyCity;
    private String companyProvince;
    private String companyCountry;

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
