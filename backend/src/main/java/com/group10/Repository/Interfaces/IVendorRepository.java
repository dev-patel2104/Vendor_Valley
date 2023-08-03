package com.group10.Repository.Interfaces;

import com.group10.Model.SignUpModel;
import com.group10.Model.VendorDashboard;

import java.sql.SQLException;
import java.util.List;

public interface IVendorRepository extends IUserRepository{
    public boolean addUser(SignUpModel signUpModel) throws SQLException;
    public VendorDashboard getStatistics(int vendorId) throws SQLException;
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;
    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException;
}
