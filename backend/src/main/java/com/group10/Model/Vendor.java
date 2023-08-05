package com.group10.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vendor {
    /**
     * The ID of the user.
     */
    private int userId;

    /**
     * The role of the user.
     */
    private String userRole;

    /**
     * The name of the company associated with the user (if applicable).
     */
    private String companyName;

    /**
     * The email of the company associated with the user (if applicable).
     */
    private String companyEmail;

    /**
     * The registration ID of the company associated with the user (if applicable).
     */
    private String companyRegistrationID;

    /**
     * The mobile number of the company associated with the user (if applicable).
     */
    private String companyMobile;

    /**
     * The street address of the company associated with the user (if applicable).
     */
    private String companyStreet;

    /**
     * The city of the company associated with the user (if applicable).
     */
    private String companyCity;

    /**
     * The province of the company associated with the user (if applicable).
     */
    private String companyProvince;

    /**
     * The country of the company associated with the user (if applicable).
     */
    private String companyCountry;


    /**
     * Sets the user ID for this entity.
     *
     * @param userId The new user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
