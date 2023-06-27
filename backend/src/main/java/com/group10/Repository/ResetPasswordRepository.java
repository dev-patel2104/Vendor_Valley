package com.group10.Repository;

import java.sql.*;
import java.time.LocalDateTime;

import com.group10.Constants.IntegerConstants;
import com.group10.Util.SqlQueries.SQLQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;



@Repository
public class ResetPasswordRepository {

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    public boolean storeVerificationCode(int id, int code) throws SQLException {
        try (Connection connection = DriverManager.getConnection(datasourceURL,
                datasourceUserName,
                datasourcePassword);
             PreparedStatement statement = connection.prepareStatement(SQLQuery.insertUserResetPasswordEntry)) {
            statement.setInt(1, id);
            statement.setInt(2, code);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            int rows = statement.executeUpdate();
            // Row Inserted
            if (rows>0) {
                return true;
            } else {
                // row not inserted
                return false;
            }
        } 
        catch (SQLException e) {
            // Handle exception
            throw new SQLException("Database Connection is lost!");
        }
    } 

    public int getVerificationCode(String email) throws SQLException {
        try (Connection connection = DriverManager.getConnection(datasourceURL,
                datasourceUserName,
                datasourcePassword);
             PreparedStatement statement = connection.prepareStatement(SQLQuery.getPasswordRestInfoByUserId)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            // Row Inserted
            if (resultSet.next()) {
                int code = resultSet.getInt("verification_code");
                Timestamp created_at =  resultSet.getTimestamp("created_at");
                Timestamp sixHoursAgo = new Timestamp(System.currentTimeMillis() - IntegerConstants.sixHoursInMilliSeconds);
                if (created_at.before(sixHoursAgo)) {
                    // created_at is older than 6 hours
                    return 0;
                }
                return code;
            } else {
                // row not inserted
                return -1;
            }
        } 
        catch (SQLException e) {
            // Handle exception
            throw new SQLException("Database Connection is lost!");
        }
    } 
}
