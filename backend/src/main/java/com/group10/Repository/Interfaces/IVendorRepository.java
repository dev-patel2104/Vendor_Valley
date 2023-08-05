package com.group10.Repository.Interfaces;

import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface that provides methods for interacting with vendor-related data in the repository.
 */
public interface IVendorRepository extends IUserRepository {
    /**
     * Adds a new vendor based on the provided sign-up model.
     *
     * @param signUpModel The sign-up model containing vendor details.
     * @return true if the vendor is successfully added, false otherwise.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public boolean addUser(SignUpModel signUpModel) throws SQLException;

    /**
     * Retrieves statistics for a specific vendor's dashboard.
     *
     * @param vendorId The ID of the vendor for whom to retrieve statistics.
     * @return VendorDashboard object containing statistics for the vendor.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public VendorDashboard getStatistics(int vendorId) throws SQLException;

    /**
     * Retrieves a list of users based on the provided user IDs.
     *
     * @param userIds The list of user IDs to retrieve.
     * @return A list of users matching the specified user IDs.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;

    /**
     * Edits the details of a company associated with a vendor.
     *
     * @param updatedDetails The updated sign-up model containing edited details.
     * @return The updated sign-up model with edited company details.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException;
}
