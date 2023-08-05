package com.group10.Service;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Booking;
import com.group10.Model.Category;
import com.group10.Model.SignUpModel;
import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Repository.Interfaces.IImageRepository;
import com.group10.Repository.Interfaces.IServiceRepository;
import com.group10.Repository.Interfaces.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class VendorProfileService extends ProfileService
{

    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private IImageRepository serviceImageRepository;
    @Autowired
    private IVendorRepository VendorRepositoryImpl;
    @Autowired
    private ICategoryRepository categoryRepository;

    /**
     * Retrieves a list of bookings associated with the provided user ID.
     *
     * @param userId The ID of the user for whom to retrieve bookings.
     * @return A list of bookings associated with the user.
     * @throws UserDoesntExistException If the user doesn't exist.
     * @throws SQLException If there's an issue accessing the database.
     */
    @Override
    public List<Booking> getBookings(int userId) throws UserDoesntExistException, SQLException {
        if (userId < 0) {
            throw new UserDoesntExistException("No such user is present");
        }

        log.info("Fetching bookings for user with ID: " + userId);

        try {
            return VendorRepositoryImpl.getBookings(userId);
        } catch (SQLException e) {
            log.error("An error occurred while retrieving bookings: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of services associated with the provided user ID.
     *
     * @param userId The ID of the user for whom to retrieve services.
     * @return A list of services associated with the user.
     * @throws UserDoesntExistException If the user doesn't exist.
     * @throws SQLException If there's an issue accessing the database.
     */
    public List<com.group10.Model.Service> getServices(int userId) throws UserDoesntExistException, SQLException {
        if (userId < 0) {
            throw new UserDoesntExistException("No such user is present");
        }

        log.info("Fetching services for user with ID: " + userId);

        try {
            return serviceRepository.getServicesForVendor(userId);
        } catch (SQLException e) {
            log.error("An error occurred while retrieving services: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Retrieves a list of categories.
     *
     * @return A list of categories.
     * @throws SQLException If there's an issue accessing the database.
     */
    public List<Category> getCategories() throws SQLException {
        log.info("Fetching categories.");

        try {
            return categoryRepository.getCategories();
        } catch (SQLException e) {
            log.error("An error occurred while retrieving categories: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Adds a new service along with its categories to the system.
     *
     * @param service The service to be added.
     * @param categoryList The list of categories associated with the service.
     * @return The added service.
     * @throws SQLException If there's an issue accessing the database.
     * @throws NoInformationFoundException If any required information is missing.
     */
    public com.group10.Model.Service addService(com.group10.Model.Service service, List<Category> categoryList) throws SQLException, NoInformationFoundException {
        log.info("Adding a new service.");

        if (service.getServiceName() == null || service.getServiceName().isEmpty()) {
            throw new NoInformationFoundException("Service name is absent");
        } else if (service.getServiceDescription() == null || service.getServiceDescription().isEmpty()) {
            throw new NoInformationFoundException("Service description is absent");
        } else if (service.getServicePrice() == null || service.getServicePrice().isEmpty()) {
            throw new NoInformationFoundException("Service price is absent");
        } else if (service.getCategoryNames() == null || service.getCategoryNames().isEmpty()) {
            throw new NoInformationFoundException("Service category is absent");
        } else if (service.getImages() == null || service.getImages().isEmpty()) {
            throw new NoInformationFoundException("Service images are absent");
        }
        if (categoryList == null || categoryList.isEmpty()) {
            throw new NoInformationFoundException("No category list is present");
        }

        try {
            return serviceRepository.insertService(service, categoryList);
        } catch (SQLException e) {
            log.error("An error occurred while adding a service: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Edits the company details of a user.
     *
     * @param updatedDetails The updated company details of the user.
     * @return The updated user details.
     * @throws SQLException If there's an issue accessing the database.
     * @throws NoInformationFoundException If the requested input is missing or incomplete.
     */
    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException, NoInformationFoundException {
        log.info("Editing company details for user.");

        if (updatedDetails == null) {
            throw new NoInformationFoundException("Requested input is missing");
        } else if (updatedDetails.getUserId() < 0) {
            throw new NoInformationFoundException("The userId for the user to be updated is not available");
        }

        try {
            return VendorRepositoryImpl.editCompanyDetails(updatedDetails);
        } catch (SQLException e) {
            log.error("An error occurred while editing company details: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Deletes a service.
     *
     * @param serviceToDelete The service to be deleted.
     * @return true if the service was successfully deleted, false otherwise.
     * @throws SQLException If there's an issue accessing the database.
     * @throws NoInformationFoundException If the requested input is missing or invalid.
     */
    public boolean deleteService(com.group10.Model.Service serviceToDelete) throws SQLException, NoInformationFoundException {
        log.info("Deleting service.");

        if (serviceToDelete == null) {
            throw new NoInformationFoundException("Requested input is missing");
        } else if (serviceToDelete.getServiceId() <= 0) {
            throw new NoInformationFoundException("The serviceId for the service to be deleted is not a valid one");
        }

        try {
            return serviceRepository.deleteService(serviceToDelete);
        } catch (SQLException e) {
            log.error("An error occurred while deleting service: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }


    /**
     * Edits a service along with its associated categories and images.
     *
     * @param serviceToUpdate The updated service information.
     * @param categoryList The list of categories associated with the service.
     * @return The updated service after successful editing, or null if an error occurred during image editing.
     * @throws SQLException If there's an issue accessing the database.
     * @throws NoInformationFoundException If the requested input is missing or invalid.
     */
    public com.group10.Model.Service editService(com.group10.Model.Service serviceToUpdate, List<Category> categoryList)
            throws SQLException, NoInformationFoundException {
        log.info("Editing service.");

        if (serviceToUpdate == null || categoryList == null || categoryList.isEmpty()) {
            throw new NoInformationFoundException("Requested input is missing");
        } else if (serviceToUpdate.getServiceId() < 0) {
            throw new NoInformationFoundException("The serviceId for the service to be updated is not a valid one");
        }

        try {
            if (serviceToUpdate.getImages() != null && !serviceToUpdate.getImages().isEmpty()) {
                serviceToUpdate = serviceImageRepository.editServiceImage(serviceToUpdate);
            }

            if (serviceToUpdate == null) {
                log.error("An error occurred while editing service images.");
                return null;
            }

            return serviceRepository.editService(serviceToUpdate, categoryList);
        } catch (SQLException e) {
            log.error("An error occurred while editing service: " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }

}
