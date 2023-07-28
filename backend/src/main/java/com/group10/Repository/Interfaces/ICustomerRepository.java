package com.group10.Repository.Interfaces;

import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerRepository extends IUserRepository
{
    public User findByEmail(String email) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
    public boolean addUser(SignUpModel signUpModel) throws SQLException;
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;

}
