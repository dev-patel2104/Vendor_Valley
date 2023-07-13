package com.group10.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class SignUpModel {

    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("is_vendor")
    private Integer isVendor;
    @JsonProperty("street")
    private String street;
    @JsonProperty("city")
    private String city;
    @JsonProperty("province")
    private String province;
    @JsonProperty("country")
    private String country;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
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
