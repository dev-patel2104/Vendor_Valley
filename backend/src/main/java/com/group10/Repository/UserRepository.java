package com.group10.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.group10.Model.User;
import com.group10.Util.UserUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String DBURL;

    @Value("${spring.datasource.username}")
    private String DBUSERNAME;

    @Value("${spring.datasource.password}")
    private String DBPASSWORD;

    @Autowired
    private UserUtil UserUtilObj;
    
    @Autowired
    private User user;

    public User findByEmail(String email) throws SQLException {
       try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");) 
        {
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery();) 
            {
                // User found
                if (resultSet.next()) {
                    // Set other properties as needed
                    user = UserUtilObj.mapResultSetToUser(resultSet);
                    return user;
                } else {
                    // User not found
                    return null;
                }
            }
        } 
        catch (SQLException e) {
            // Handle exception
            throw new SQLException("Database Connection is lost!");
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, street = ?, city = ?, province = ?, country = ?, email = ?, mobile = ?, is_vendor = ?, password = ?  WHERE (user_id = ?)";
        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);) 
        {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getStreet());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getProvince());
            statement.setString(6, user.getCountry());
            statement.setString(7, user.getEmail());
            statement.setString(8, user.getMobile());
            statement.setInt(9, user.getVendor());
            statement.setString(10, user.getPassword());
            statement.setInt(11, user.getUserId());
       
            int rowsUpdated = statement.executeUpdate();
            // User found
            if (rowsUpdated > 0) {
                // Updated properties as needed
                return true;
            } 
            // User not found
            return false;
        } 
        catch (SQLException e) {
            // Handle exception
            throw new SQLException("Database Connection is lost!");
        }
    }
}