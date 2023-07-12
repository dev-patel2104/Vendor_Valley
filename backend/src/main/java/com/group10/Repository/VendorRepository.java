package com.group10.Repository;

import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Model.Vendor;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
public class VendorRepository{

    @Autowired
    DatabaseService databaseService;

    @Autowired
    private UserRepository userRepository;

    public boolean saveVendor(User user, Vendor vendorModel) throws SQLException{

        try(Connection connection = databaseService.connect();)
        {
            try (PreparedStatement sqlPreparedStatement = connection.prepareStatement(SQLQuery.insertVendorQuery);
                 PreparedStatement addUserPreparedStatement = connection.prepareStatement(SQLQuery.addUserQuery, Statement.RETURN_GENERATED_KEYS);) {
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

}
