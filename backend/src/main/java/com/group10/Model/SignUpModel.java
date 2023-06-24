package com.group10.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class SignUpModel {

    //TODO: add json properties based on FE call
    private Integer userId;
    private String firstName;
    private String lastName;
    private String mobile;
    private Integer isVendor;
    private String street;
    private String city;
    private String province;
    private String country;
    private String email;
    private String password;

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

    public User buildUserModel() {
        return User.builder().
                userId(getUserId()).
                firstName(getFirstName()).
                lastName(getLastName()).
                mobile(getMobile()).
                isVendor(getIsVendor()).
                street(getStreet()).
                city(getCity()).
                province(getProvince())
                .country(getCountry()).
                email(getEmail()).
                password(getPassword()).
                build();
    }

    public Vendor buildVendorModel() {
        return Vendor.builder().
                userRole(getUserRole()).
                companyName(getCompanyName()).
                companyEmail(getCompanyEmail()).
                companyRegistrationID(getCompanyRegistrationID()).
                companyMobile(getCompanyMobile()).
                companyStreet(getCompanyStreet()).
                companyCity(getCompanyCity()).
                companyProvince(getCompanyProvince()).
                companyCountry(getCompanyCountry()).
                build();
    }
}
