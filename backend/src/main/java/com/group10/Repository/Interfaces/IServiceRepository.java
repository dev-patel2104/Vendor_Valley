package com.group10.Repository.Interfaces;

import com.group10.Model.Category;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;


/**
 * An interface that provides methods for interacting with service-related data in the repository.
 */
public interface IServiceRepository
{
    /**
     * Retrieves a list of services based on the provided search parameter.
     *
     * @param searchParam The search parameter used to filter services.
     * @return A list of services matching the search criteria.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Service> getServicesBasedOnSearchParam(String searchParam) throws SQLException;

    /**
     * Retrieves the details of a specific service.
     *
     * @param serviceId The ID of the service to retrieve.
     * @return The details of the requested service.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public Service getServiceDetails(int serviceId) throws SQLException;

    /**
     * Retrieves a list of services associated with a vendor (user).
     *
     * @param userID The ID of the vendor (user).
     * @return A list of services associated with the specified vendor.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Service> getServicesForVendor(int userID) throws SQLException;

    /**
     * Edits the details of a service.
     *
     * @param serviceToUpdate The updated service object.
     * @param categoryList The list of categories associated with the service.
     * @return The edited service object.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public Service editService(Service serviceToUpdate, List<Category> categoryList) throws SQLException;

    /**
     * Inserts a new service into the repository.
     *
     * @param service The service object to insert.
     * @param categoryList The list of categories associated with the service.
     * @return The inserted service object.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public Service insertService(Service service, List<Category> categoryList) throws SQLException;

    /**
     * Deletes a service from the repository.
     *
     * @param serviceToDelete The service object to delete.
     * @return true if the service is successfully deleted, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean deleteService(Service serviceToDelete) throws SQLException;

    /**
     * Checks if a service with the given ID exists.
     *
     * @param serviceId The ID of the service to check.
     * @return true if the service exists, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean checkIfServiceExists(int serviceId) throws SQLException;

    /**
     * Checks if a booking for a specific service and user exists.
     *
     * @param bookingId The ID of the booking.
     * @param serviceId The ID of the service.
     * @param userId The ID of the user.
     * @return true if the booking exists, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean checkIfBookingExists(int bookingId, int serviceId, int userId) throws SQLException;
}
