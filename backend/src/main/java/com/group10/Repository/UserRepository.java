package com.group10.Repository;

import com.group10.Enums.SignUpUserSQLQueryEnum;
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

        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
             PreparedStatement statement = connection.prepareStatement(SQLQuery.updateUserQuery)) {
            statement.setString(SignUpUserSQLQueryEnum.USER_FIRSTNAME.queryIndex, user.getFirstName());
            statement.setString(SignUpUserSQLQueryEnum.USER_LASTNAME.queryIndex, user.getLastName());
            statement.setString(SignUpUserSQLQueryEnum.USER_STREET.queryIndex, user.getStreet());
            statement.setString(SignUpUserSQLQueryEnum.USER_CITY.queryIndex, user.getCity());
            statement.setString(SignUpUserSQLQueryEnum.USER_PROVINCE.queryIndex, user.getProvince());
            statement.setString(SignUpUserSQLQueryEnum.USER_COUNTRY.queryIndex, user.getCountry());
            statement.setString(SignUpUserSQLQueryEnum.USER_EMAIL.queryIndex, user.getEmail());
            statement.setString(SignUpUserSQLQueryEnum.USER_MOBILE.queryIndex, user.getMobile());
            statement.setInt(SignUpUserSQLQueryEnum.USER_IS_VENDOR.queryIndex, user.getVendor());
            statement.setString(SignUpUserSQLQueryEnum.USER_PASSWORD.queryIndex, user.getPassword());
            statement.setInt(SignUpUserSQLQueryEnum.USER_ID.queryIndex, user.getUserId());

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

    public boolean addUser(User user) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQuery.addUserQuery)) {

            if(findByEmail(user.getEmail()) != null) {
                return false;
            }

            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_FIRSTNAME.queryIndex, user.getFirstName());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_LASTNAME.queryIndex, user.getLastName());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_STREET.queryIndex, user.getStreet());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_CITY.queryIndex, user.getCity());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_PROVINCE.queryIndex, user.getProvince());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_COUNTRY.queryIndex, user.getCountry());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_EMAIL.queryIndex, user.getEmail());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_MOBILE.queryIndex, user.getMobile());
            addUserPreparedStatement.setInt(SignUpUserSQLQueryEnum.USER_IS_VENDOR.queryIndex, user.getVendor());
            addUserPreparedStatement.setString(SignUpUserSQLQueryEnum.USER_PASSWORD.queryIndex, user.getPassword());
            addUserPreparedStatement.executeUpdate();

            return true;
        }
        catch(SQLException e) {
            throw new SQLException("data not being added");
        }
    }

}