package com.group10.Repository;

import com.group10.Model.Category;
import com.group10.Model.Service;

import java.sql.SQLException;
import java.util.List;

public interface IServiceRepositoryWriter {
    public Service editService(Service serviceToUpdate, List<Category> categoryList) throws SQLException;
    public Service insertService(Service service, List<Category> categoryList) throws SQLException;
    public boolean deleteService(Service serviceToDelete) throws SQLException;
    public Service editServiceImage(Service service) throws SQLException;
}
