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

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public void setTotalBookingsForService(int totalBookingsForService)
    {
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(String totalBookings) {
        this.totalBookings = totalBookings;
    }
}
