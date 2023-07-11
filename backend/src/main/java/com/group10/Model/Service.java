package com.group10.Model;

public class Service
{
    private int serviceId;
    private int userId;
    private String serviceName;
    private String serviceDescription;
    private String servicePrice;
    private int totalBookingsForService;

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
    public void setTotalBookingsForService(int totalBookingsForService)
    {
        this.totalBookingsForService = totalBookingsForService;
    }
}
