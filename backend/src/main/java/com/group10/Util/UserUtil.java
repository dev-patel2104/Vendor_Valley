package com.group10.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.group10.Model.User;

@Component
public class UserUtil {
    public User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setLastName(resultSet.getString("last_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setMobile(resultSet.getString("mobile"));
        user.setVendor(resultSet.getInt("is_vendor"));
        user.setEmail(resultSet.getString("email"));
        user.setStreet(resultSet.getString("street"));
        user.setCity(resultSet.getString("city"));
        user.setProvince(resultSet.getString("province"));
        user.setCountry(resultSet.getString("country"));
        user.setPassword(resultSet.getString("password"));
        // Set other properties as needed
        return user;
    }
}
