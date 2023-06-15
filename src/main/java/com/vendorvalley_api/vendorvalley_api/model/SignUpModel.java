package com.vendorvalley_api.vendorvalley_api.model;

import lombok.Builder;

@Builder
public class SignUpModel {

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

}
