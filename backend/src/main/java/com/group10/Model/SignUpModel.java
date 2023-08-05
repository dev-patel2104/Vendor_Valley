package com.group10.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class SignUpModel {

    /**
     * The ID of the user.
     */
    @JsonProperty("user_id")
    private Integer userId;

    /**
     * The first name of the user.
     */
    @JsonProperty("first_name")
    private String firstName;

    /**
     * The last name of the user.
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * The mobile number of the user.
     */
    @JsonProperty("mobile")
    private String mobile;

    /**
     * Flag indicating whether the user is a vendor (1) or not (0).
     */
    @JsonProperty("is_vendor")
    private Integer isVendor;

    /**
     * The street address of the user.
     */
    @JsonProperty("street")
    private String street;

    /**
     * The city of the user's location.
     */
    @JsonProperty("city")
    private String city;

    /**
     * The province of the user's location.
     */
    @JsonProperty("province")
    private String province;

    /**
     * The country of the user's location.
     */
    @JsonProperty("country")
    private String country;

    /**
     * The email address of the user.
     */
    @JsonProperty("email")
    private String email;

    /**
     * The password of the user.
     */
    @JsonProperty("password")
    private String password;


    /**
     * The role of the user.
     */
    @JsonProperty("user_role")
    private String userRole;

    /**
     * The name of the company associated with the user.
     */
    @JsonProperty("company_name")
    private String companyName;

    /**
     * The email address of the company.
     */
    @JsonProperty("company_email")
    private String companyEmail;

    /**
     * The registration number of the company.
     */
    @JsonProperty("company_registration_number")
    private String companyRegistrationID;

    /**
     * The mobile number of the company.
     */
    @JsonProperty("company_mobile")
    private String companyMobile;

    /**
     * The street address of the company.
     */
    @JsonProperty("company_street")
    private String companyStreet;

    /**
     * The city where the company is located.
     */
    @JsonProperty("company_city")
    private String companyCity;

    /**
     * The province where the company is located.
     */
    @JsonProperty("company_province")
    private String companyProvince;

    /**
     * The country where the company is located.
     */
    @JsonProperty("company_country")
    private String companyCountry;


    /**
     * Builds and returns a User model using the current object.
     *
     * @return A User model with the specified field values.
     */
    public User buildUserModel() {
        return User.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .mobile(getMobile())
                .isVendor(getIsVendor())
                .street(getStreet())
                .city(getCity())
                .province(getProvince())
                .country(getCountry())
                .email(getEmail())
                .password(getPassword())
                .build();
    }

    /**
     * Builds and returns a Vendor model using the current object.
     *
     * @return A Vendor model with the specified field values.
     */
    public Vendor buildVendorModel() {
        return Vendor.builder()
                .userRole(getUserRole())
                .companyName(getCompanyName())
                .companyEmail(getCompanyEmail())
                .companyRegistrationID(getCompanyRegistrationID())
                .companyMobile(getCompanyMobile())
                .companyStreet(getCompanyStreet())
                .companyCity(getCompanyCity())
                .companyProvince(getCompanyProvince())
                .companyCountry(getCompanyCountry())
                .build();
    }


    /**
     * Generates a string representation of the SignUp object.
     *
     * @return A string containing the details of the signup.
     */
    @Override
    public String toString() {
        return "SignUpModel{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isVendor=" + isVendor +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyRegistrationID='" + companyRegistrationID + '\'' +
                ", companyMobile='" + companyMobile + '\'' +
                ", companyStreet='" + companyStreet + '\'' +
                ", companyCity='" + companyCity + '\'' +
                ", companyProvince='" + companyProvince + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                '}';
    }
}
