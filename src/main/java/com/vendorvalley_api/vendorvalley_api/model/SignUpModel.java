package com.vendorvalley_api.vendorvalley_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpModel {

    private Integer userId;

    @JsonProperty("user_role")
    private String userRole;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_email")
    private String companyEmail;

    @JsonProperty("company_registration_number")
    private String companyRegistrationID;

    @JsonProperty("company_mobile")
    private String companyMobile;

    @JsonProperty("company_street")
    private String companyStreet;

    @JsonProperty("company_city")
    private String companyCity;

    @JsonProperty("company_province")
    private String companyProvince;

    @JsonProperty("company_country")
    private String companyCountry;

}
