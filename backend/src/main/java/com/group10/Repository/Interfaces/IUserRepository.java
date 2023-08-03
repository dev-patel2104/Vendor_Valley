package com.group10.Repository.Interfaces;

import java.sql.SQLException;
import java.util.List;

import com.group10.Model.Booking;
import com.group10.Model.SignUpModel;

public interface IUserRepository {
    public boolean addUser(SignUpModel signUpModel) throws SQLException;
    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException;
    public List<Booking> getBookings(int userId) throws SQLException;
}
