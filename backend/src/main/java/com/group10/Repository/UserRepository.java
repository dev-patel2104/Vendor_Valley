package com.group10.Repository;

import com.group10.Enums.UserTableColumns;
import com.group10.Model.SignUpModel;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Util.MapResultSetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Constants.IntegerConstants;
import com.group10.Model.User;

import java.sql.*;

/**
 * Repository class for managing user data.
 */
@Repository
public class UserRepository{

    @Autowired
    DatabaseService databaseService;


    @Autowired
    private User user;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Finds a user by their email in the database.
     *
     * @param email The email of the user to find.
     * @return The user object if found, or null if not found.
     * @throws SQLException If there is an error with the database connection.
     */
    public User findByEmail(String email) throws SQLException {

        try (Connection connection = databaseService.connect();
             PreparedStatement getUsersPreparedStatement = connection.prepareStatement(SQLQueries.getUserByEmailID);)
        {
            getUsersPreparedStatement.setString(1, email);
            try(ResultSet resultSet = getUsersPreparedStatement.executeQuery();)
            {
                // User found
                if (resultSet.next()) {
                    // Set other properties as needed
                    user = mapResultSetUtilObj.mapResultSetToUser(resultSet);
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

    /**
     * Updates a user's information in the database.
     *
     * @param user The User object containing the updated information.
     * @return true if the user was successfully updated, false otherwise.
     * @throws SQLException if there is an error with the database connection.
     */
    public boolean updateUser(User user) throws SQLException {

        try (Connection connection = databaseService.connect();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.updateUserQuery);) {
            
            if (user.getUserId() == IntegerConstants.userDoesntExist){
                return false;
            }
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

    /**
     * Adds a new user to the database and returns the generated user ID.
     *
     * @param user The user object to be added.
     * @return The generated user ID, or 0 if the user already exists.
     * @throws SQLException If there is an error executing the SQL query.
     */
    public int addUser(User user) throws SQLException {

        try (Connection connection = databaseService.connect();
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQueries.addUserQuery, Statement.RETURN_GENERATED_KEYS);)
        {

            if(findByEmail(user.getEmail()) != null) {
                return IntegerConstants.userAlreadyExists;
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
                int userId = rs.getInt(1);
                return userId;
            }
            return IntegerConstants.userNotInserted;
        }
        catch(SQLException e) {
            throw new SQLException("data not being added");
        }
    }

    public SignUpModel getUser(int user_id) throws SQLException {
        SignUpModel customer = null;
        try(Connection connection = databaseService.connect();
        PreparedStatement getCustomerPreparedStatement = connection.prepareStatement(SQLQueries.getUserByID))
        {
            getCustomerPreparedStatement.setInt(1,user_id);
            ResultSet rs = getCustomerPreparedStatement.executeQuery();
            //ToDo: add the check to see if cnt is greater than 1 then throw and exception saying more than one user
            while(rs.next())
            {
                customer =  SignUpModel.builder().userId(rs.getInt(1)).
                        firstName(rs.getString(2)).
                        lastName(rs.getString(3)).
                        street(rs.getString(4)).
                        city(rs.getString(5)).
                        province(rs.getString(6)).
                        country(rs.getString(7)).
                        email(rs.getString(8)).
                        mobile(rs.getString(9)).
                        isVendor(rs.getInt(10)).
                        password(rs.getString(11)).
                        userRole(rs.getString(13)).
                        companyName(rs.getString(14)).
                        companyEmail(rs.getString(15)).
                        companyRegistrationID(rs.getString(16)).
                        companyMobile(rs.getString(17)).
                        companyStreet(rs.getString(18)).
                        companyCity(rs.getString(19)).
                        companyProvince(rs.getString(20)).
                        companyCountry(rs.getString(21)).
                        build();
                
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
       return customer;
    }
}