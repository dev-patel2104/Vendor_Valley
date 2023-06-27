package com.group10.Repository;

import com.group10.Model.SignUpModel;
import com.group10.Util.SqlQueries.SQLQuery;
import com.group10.Util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.group10.Model.User;

import java.sql.*;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Autowired
    private User user;

    private UserUtil UserUtilObj = new UserUtil();


    public User findByEmail(String email) throws SQLException {

        try (Connection connection = DriverManager.getConnection(datasourceURL,
                datasourceUserName,
                datasourcePassword);
             PreparedStatement getUsersPreparedStatement = connection.prepareStatement(SQLQuery.getUserByEmailID);)
        {
            getUsersPreparedStatement.setString(1, email);
            try(ResultSet resultSet = getUsersPreparedStatement.executeQuery();)
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

        try (Connection connection = DriverManager.getConnection(datasourceURL,
                datasourceUserName,
                datasourcePassword);
             PreparedStatement statement = connection.prepareStatement(SQLQuery.updateUserQuery);) {
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

    public int addUser(User user) throws SQLException {

        try (Connection connection = DriverManager.getConnection(datasourceURL,
                datasourceUserName,
                datasourcePassword);
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQuery.addUserQuery, Statement.RETURN_GENERATED_KEYS);)
        {

            int userId = 0;

            if(findByEmail(user.getEmail()) != null) {
                return userId;
            }
            addUserPreparedStatement.setString(1, user.getFirstName());
            addUserPreparedStatement.setString(2, user.getLastName());
            addUserPreparedStatement.setString(3, user.getStreet());
            addUserPreparedStatement.setString(4, user.getCity());
            addUserPreparedStatement.setString(5, user.getProvince());
            addUserPreparedStatement.setString(6, user.getCountry());
            addUserPreparedStatement.setString(7, user.getEmail());
            addUserPreparedStatement.setString(8, user.getMobile());
            addUserPreparedStatement.setInt(9, user.getVendor());
            addUserPreparedStatement.setString(10, user.getPassword());

            addUserPreparedStatement.executeUpdate();
            ResultSet rs = addUserPreparedStatement.getGeneratedKeys();

            if (rs.next()) {
                userId = rs.getInt(1);
            }
            return userId;
        }
        catch(SQLException e) {
            throw new SQLException("data not being added");
        }
    }

}