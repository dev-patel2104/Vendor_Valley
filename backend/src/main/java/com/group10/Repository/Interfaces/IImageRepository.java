package com.group10.Repository.Interfaces;

import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface IImageRepository
{
    public List<Service> getImagesForService(List<Service> servicesList, String searchParam) throws SQLException;
    public Service editServiceImage(Service service) throws SQLException;
}
