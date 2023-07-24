package com.group10.Repository;

import com.group10.Model.SignUpModel;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import com.group10.Util.MapResultSetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group10.Constants.Constants;
import com.group10.Model.User;
import com.group10.Repository.Interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing user data.
 */
@Repository
public class CustomerRepositoryImpl implements IUserRepository{

    @Autowired
    IDatabaseService databaseService;


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
            
            if (user.getUserId() == Constants.USERDOESNTEXIST){
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
    public boolean addUser(SignUpModel signUpModel) throws SQLException {

        User user = signUpModel.buildUserModel();
        try (Connection connection = databaseService.connect();
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQueries.addUserQuery, Statement.RETURN_GENERATED_KEYS);)
        {

            if(findByEmail(user.getEmail()) != null) {
                return false;
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
                return true;
            }
            return false;
        }
        catch(SQLException e) {
            throw new SQLException("data not being added");
        }
    }

    public List<SignUpModel> getUsers(List<Integer> userIds) throws SQLException {
        SignUpModel user = null;
        List<SignUpModel> userList = new ArrayList<>();
        int user_id = userIds.get(0);
        try(Connection connection = databaseService.connect();
        PreparedStatement getCustomerPreparedStatement = connection.prepareStatement(SQLQueries.getUserByID))
        {
            getCustomerPreparedStatement.setInt(1,user_id);
            ResultSet rs = getCustomerPreparedStatement.executeQuery();
            //ToDo: add the check to see if cnt is greater than 1 then throw and exception saying more than one user
            while(rs.next())
            {
                user =  mapResultSetUtilObj.mapResultSetToSignUpModel(rs);
                userList.add(user);
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
       return userList;
    }


}