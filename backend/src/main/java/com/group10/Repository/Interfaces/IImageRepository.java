package com.group10.Repository.Interfaces;

import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface that provides methods for managing images related to services.
 */
public interface IImageRepository
{
    /**
     * Retrieves images for a list of services based on a search parameter.
     *
     * @param servicesList The list of services to retrieve images for.
     * @param searchParam The search parameter for filtering images.
     * @return A list of services with retrieved images.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public List<Service> getImagesForService(List<Service> servicesList, String searchParam) throws SQLException;

    /**
     * Edits the image associated with a service.
     *
     * @param service The service object containing updated image information.
     * @return The updated service object.
     * @throws SQLException if there's an issue with the database interaction.
     */
    public Service editServiceImage(Service service) throws SQLException;
}
