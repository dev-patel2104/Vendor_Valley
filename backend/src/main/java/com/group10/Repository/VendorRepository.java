package com.group10.Repository;

import com.group10.Model.*;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.MapResultSetUtil;
import com.group10.Util.SqlQueries.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository class for managing vendor data in the database.
 */
@Repository
public class VendorRepository{

    @Autowired
    IDatabaseService databaseService;

    @Autowired
    private MapResultSetUtil mapResultSetUtilObj;

    /**
     * Saves a vendor and associated user information to the database.
     *
     * @param user The user object containing the vendor's information.
     * @param vendorModel The vendor object to be saved.
     * @return true if the vendor and user information were successfully saved, false otherwise.
     * @throws SQLException if there is an error with the database connection or query execution.
     */
    public boolean saveVendor(User user, Vendor vendorModel) throws SQLException{

        try(Connection connection = databaseService.connect();)
        {
            try (PreparedStatement sqlPreparedStatement = connection.prepareStatement(SQLQueries.insertVendorQuery);
                PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQueries.addUserQuery, Statement.RETURN_GENERATED_KEYS);) {
                int userId = 0;
                connection.setAutoCommit(false);
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
                sqlPreparedStatement.setInt(1, userId);
                sqlPreparedStatement.setString(2, vendorModel.getUserRole());
                sqlPreparedStatement.setString(3, vendorModel.getCompanyCity());
                sqlPreparedStatement.setString(4, vendorModel.getCompanyCountry());
                sqlPreparedStatement.setString(5, vendorModel.getCompanyEmail());
                sqlPreparedStatement.setString(6, vendorModel.getCompanyMobile());
                sqlPreparedStatement.setString(7, vendorModel.getCompanyName());
                sqlPreparedStatement.setString(8, vendorModel.getCompanyProvince());
                sqlPreparedStatement.setString(9, vendorModel.getCompanyRegistrationID());
                sqlPreparedStatement.setString(10, vendorModel.getCompanyStreet());
                sqlPreparedStatement.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException(e);
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return true;
    }

    public VendorDashboard getStatistics(int vendorId) throws SQLException{
        VendorDashboard vendorDashboard = null;
        try(Connection connect = databaseService.connect();
        PreparedStatement preparedStatement = connect.prepareStatement(SQLQueries.vendorDashboardInfoQuery);
        ){
            preparedStatement.setInt(1, vendorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               vendorDashboard = mapResultSetUtilObj.mapResultSetToVendorDashboard(resultSet);
            }
        }
        return vendorDashboard;
    }

    public List<User> getCustomerInfo(List<Integer> userIds) throws SQLException{
        List<User> users = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT user_id, first_name, last_name, email, city, country FROM users WHERE user_id IN (");
        for (int i = 0; i < userIds.size(); i++) {
            queryBuilder.append("?");
            if (i < userIds.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(");");
        String query = queryBuilder.toString();
        try (Connection connection = databaseService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
                for (int i = 0; i < userIds.size(); i++) {
                    preparedStatement.setInt(i + 1, userIds.get(i));
                }
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setUserId(rs.getInt("user_id"));
                        user.setFirstName(rs.getString("first_name"));
                        user.setLastName(rs.getString("last_name"));
                        user.setEmail(rs.getString("email"));
                        user.setCity(rs.getString("city"));
                        user.setCountry(rs.getString("country"));
                        users.add(user);
                    }
                    return users;
                }
        } 
        catch (SQLException e) {
            throw new SQLException("Datebase connection lost!");
        }
    }

    public List<Booking> getBookingsInfo(int userId) throws SQLException
    {
        List<Booking> bookingList = new ArrayList<>();
        try(Connection connection = databaseService.connect();
        PreparedStatement statement = connection.prepareStatement(SQLQueries.getVendorBookings);)
        {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            Booking booking = null;
            User user = null;
            while(rs.next())
            {
                booking = mapResultSetUtilObj.mapResultSetToBooking(rs);
                if(booking == null)
                {
                    System.out.println("There is a booking in the database with no customer associated with it");
                    continue;
                }
                bookingList.add(booking);
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return bookingList;
    }

    public SignUpModel editCompanyDetails(SignUpModel updatedDetails) throws SQLException
    {
        Vendor vendor = updatedDetails.buildVendorModel();
        vendor.setUserId(updatedDetails.getUserId());
        try(Connection connect = databaseService.connect();
        PreparedStatement statement = connect.prepareStatement(SQLQueries.updateCompanyDetailsQuery);)
        {
            statement.setString(1,vendor.getUserRole());
            statement.setString(2, vendor.getCompanyName());
            statement.setString(3, vendor.getCompanyEmail());
            statement.setString(4, vendor.getCompanyRegistrationID());
            statement.setString(5, vendor.getCompanyMobile());
            statement.setString(6,vendor.getCompanyStreet());
            statement.setString(7, vendor.getCompanyCity());
            statement.setString(8,vendor.getCompanyProvince());
            statement.setString(9, vendor.getCompanyCountry());
            statement.setInt(10, vendor.getUserId());

            int rowsUpdated = statement.executeUpdate();
            // User found
            if (rowsUpdated > 0) {
                // Updated properties as needed
                return updatedDetails;
            }
            // User not found
            return null;
        }
        catch (SQLException e)
        {
            throw new SQLException("Database Issue");
        }

    }
}
