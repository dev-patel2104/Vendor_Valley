package com.group10.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Represents a user with various properties such as userId, lastName, firstName, and mobile.
 */
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * The unique identifier for the user.
     */
    private Integer userId;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The mobile number of the user.
     */
    private String mobile;

    /**
     * Indicates whether the user is a vendor or not (1 for vendor, 0 for not).
     */
    private Integer isVendor;

    /**
     * The street address of the user.
     */
    private String street;

    /**
     * The city where the user resides.
     */
    private String city;

    /**
     * The province or state where the user resides.
     */
    private String province;

    /**
     * The country where the user resides.
     */
    private String country;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password associated with the user's account.
     */
    private String password;


    /**
     * Retrieves the email address associated with the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email The new email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password associated with the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's unique identifier.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId The new unique identifier to set.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name for the user.
     *
     * @param lastName The new last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name for the user.
     *
     * @param firstName The new first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Retrieves the mobile number associated with the user.
     *
     * @return The user's mobile number.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the mobile number for the user.
     *
     * @param mobile The new mobile number to set.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Retrieves the vendor status of the user.
     *
     * @return The user's vendor status.
     */
    public Integer getVendor() {
        return isVendor;
    }

    /**
     * Sets the vendor status for the user.
     *
     * @param isVendor The new vendor status to set.
     */
    public void setVendor(Integer isVendor) {
        this.isVendor = isVendor;
    }

    /**
     * Retrieves the street address of the user.
     *
     * @return The user's street address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street address for the user.
     *
     * @param street The new street address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Retrieves the city of residence of the user.
     *
     * @return The user's city of residence.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of residence for the user.
     *
     * @param city The new city of residence to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Retrieves the province of residence of the user.
     *
     * @return The user's province of residence.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province of residence for the user.
     *
     * @param province The new province of residence to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Retrieves the country of residence of the user.
     *
     * @return The user's country of residence.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of residence for the user.
     *
     * @param country The new country of residence to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the full name of the user.
     *
     * @return The user's full name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}