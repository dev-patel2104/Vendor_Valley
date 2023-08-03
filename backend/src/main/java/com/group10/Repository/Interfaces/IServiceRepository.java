package com.group10.Repository.Interfaces;

import com.group10.Model.Category;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface IServiceRepository
{
    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException;
    public Service getServiceDetails(int serviceId) throws SQLException;
    public List<Service> getServicesForVendor(int userID) throws SQLException;
    public Service editService(Service serviceToUpdate, List<Category> categoryList) throws SQLException;
    public Service insertService(Service service, List<Category> categoryList) throws SQLException;
    public boolean deleteService(Service serviceToDelete) throws SQLException;
    public boolean checkIfServiceExists(int serviceId) throws SQLException;
    public boolean checkIfBookingExists(int bookingId, int serviceId, int userId) throws SQLException;
}
