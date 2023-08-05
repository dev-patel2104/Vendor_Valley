package com.group10.Model;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Represents a service entity.
 */
@Component
public class Service
{
    private int serviceId;
    private int userId;
    private String serviceName;
    private String serviceDescription;
    private String servicePrice;
    private List<String> images;
    private List<String> categoryNames;
    private String companyStreet;
    private String companyProvince;
    private String companyCity;
    private String companyCountry;
    private String averageRating;
    private String totalBookings;
    private String companyEmail;

    /**
     * Gets the company email associated with the service.
     *
     * @return The company email for the service.
     */
    public String getCompanyEmail() {
        return companyEmail;
    }

    /**
     * Sets the company email for the service.
     *
     * @param companyEmail The company email to be set.
     */
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    /**
     * Gets the service ID.
     *
     * @return The service ID.
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Sets the service ID.
     *
     * @param serviceId The service ID to be set.
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets the user ID associated with the service.
     *
     * @return The user ID for the service.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for the service.
     *
     * @param userId The user ID to be set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * Gets the name of the service.
     *
     * @return The name of the service.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the name of the service.
     *
     * @param serviceName The name of the service to be set.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets the description of the service.
     *
     * @return The description of the service.
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * Sets the description of the service.
     *
     * @param serviceDescription The description of the service to be set.
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     * Gets the price of the service.
     *
     * @return The price of the service.
     */
    public String getServicePrice() {
        return servicePrice;
    }

    /**
     * Sets the price of the service.
     *
     * @param servicePrice The price of the service to be set.
     */
    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    /**
     * Gets the list of images associated with the service.
     *
     * @return The list of images for the service.
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * Sets the list of images for the service.
     *
     * @param images The list of images to be set.
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * Gets the list of category names associated with the service.
     *
     * @return The list of category names for the service.
     */
    public List<String> getCategoryNames() {
        return categoryNames;
    }

    /**
     * Sets the list of category names for the service.
     *
     * @param categoryNames The list of category names to be set.
     */
    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    /**
     * Sets the total number of bookings for the service.
     * Note: This method has no functionality.
     *
     * @param totalBookingsForService The total number of bookings to be set.
     */
    public void setTotalBookingsForService(int totalBookingsForService) {}


    /**
     * Gets the street of the company's location.
     *
     * @return The street of the company's location.
     */
    public String getCompanyStreet() {
        return companyStreet;
    }

    /**
     * Sets the street of the company's location.
     *
     * @param companyStreet The street of the company's location to be set.
     */
    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    /**
     * Gets the province of the company's location.
     *
     * @return The province of the company's location.
     */
    public String getCompanyProvince() {
        return companyProvince;
    }

    /**
     * Sets the province of the company's location.
     *
     * @param companyProvince The province of the company's location to be set.
     */
    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    /**
     * Gets the city of the company's location.
     *
     * @return The city of the company's location.
     */
    public String getCompanyCity() {
        return companyCity;
    }

    /**
     * Sets the city of the company's location.
     *
     * @param companyCity The city of the company's location to be set.
     */
    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    /**
     * Gets the country of the company's location.
     *
     * @return The country of the company's location.
     */
    public String getCompanyCountry() {
        return companyCountry;
    }

    /**
     * Sets the country of the company's location.
     *
     * @param companyCountry The country of the company's location to be set.
     */
    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    /**
     * Gets the average rating of the company's services.
     *
     * @return The average rating of the company's services.
     */
    public String getAverageRating() {
        return averageRating;
    }

    /**
     * Sets the average rating of the company's services.
     *
     * @param averageRating The average rating of the company's services to be set.
     */
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Gets the total number of bookings for the company's services.
     *
     * @return The total number of bookings for the company's services.
     */
    public String getTotalBookings() {
        return totalBookings;
    }

    /**
     * Sets the total number of bookings for the company's services.
     *
     * @param totalBookings The total number of bookings for the company's services to be set.
     */
    public void setTotalBookings(String totalBookings) {
        this.totalBookings = totalBookings;
    }


    /**
     * Generates a string representation of the Service object.
     *
     * @return A string containing the details of a service.
     */
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", userId=" + userId +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", servicePrice='" + servicePrice + '\'' +
                ", images=" + images +
                ", categoryNames=" + categoryNames +
                ", companyStreet='" + companyStreet + '\'' +
                ", companyProvince='" + companyProvince + '\'' +
                ", companyCity='" + companyCity + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                ", averageRating='" + averageRating + '\'' +
                ", totalBookings='" + totalBookings + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                '}';
    }
}
