package com.group10.Repository;

import com.group10.Util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.group10.Model.User;

import java.sql.*;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String DBURL;

    @Value("${spring.datasource.username}")
    private String DBUSERNAME;

    @Value("${spring.datasource.password}")
    private String DBPASSWORD;

    @Autowired
    private User user;
    private UserUtil UserUtilObj = new UserUtil();
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

    public boolean addUser(User user) throws SQLException
    {
        if(user == null)
        {
            throw new SQLException("data not being added");
        }
        if(user.getFirstName().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getLastName().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getMobile().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getVendor() != 0 && user.getVendor() != 1)
        {
            throw new SQLException("data not being added");
        }
        if(user.getStreet().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getCity().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getProvince().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getCountry().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getEmail().isEmpty())
        {
            throw new SQLException("data not being added");
        }
        if(user.getPassword().length() < 8 || user.getPassword().isEmpty())
        {
            throw new SQLException("data not being added");
        }

        String query = "INSERT INTO users (first_name, last_name, street, city, " +
                "province, country, email, mobile, is_vendor, Password)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            PreparedStatement ps = connection.prepareStatement(query);)
        {

            if(findByEmail(user.getEmail()) != null)
            {
                return false;
            }

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getStreet());
            ps.setString(4, user.getCity());
            ps.setString(5, user.getProvince());
            ps.setString(6, user.getCountry());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getMobile());
            ps.setInt(9, user.getVendor());
            ps.setString(10, user.getPassword());
            ps.executeUpdate();

            if(user.getVendor() == 1)
            {
                // call boon's method
            }
            return true;
        }
        catch(SQLException e)
        {
            throw new SQLException("data not being added");
        }
    }

}