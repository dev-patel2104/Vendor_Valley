package com.group10.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int userId;
    private String lastName;
    private String firstName;
    private String mobile;
    private int isVendor;
    private String street;
    private String city;
    private String province;
    private String country;
    private String email;
    private String password;

    /**
     * Get the email of the customer.
     *
     * @return The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the customer.
     *
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the password of the customer.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the customer.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the user ID of the customer.
     *
     * @return The user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the user ID of the customer.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the last name of the customer.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the customer.
     *
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the first name of the customer.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the customer.
     *
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the mobile number of the customer.
     *
     * @return The mobile number.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set the mobile number of the customer.
     *
     * @param mobile The mobile number to set.
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Get the vendor status of the customer.
     *
     * @return The vendor status (1 if vendor, 0 if not).
     */
    public int getVendor() {
        return isVendor;
    }

    /**
     * Set the vendor status of the customer.
     *
     * @param isVendor The vendor status to set (1 if vendor, 0 if not).
     */
    public void setVendor(int isVendor) {
        this.isVendor = isVendor;
    }

    /**
     * Get the street address of the customer.
     *
     * @return The street address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street address of the customer.
     *
     * @param street The street address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the city of the customer.
     *
     * @return The city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city of the customer.
     *
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the province of the customer.
     *
     * @return The province.
     */
    public String getProvince() {
        return province;
    }

    /**
     * Set the province of the customer.
     *
     * @param province The province to set.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Get the country of the customer.
     *
     * @return The country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country of the customer.
     *
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

}