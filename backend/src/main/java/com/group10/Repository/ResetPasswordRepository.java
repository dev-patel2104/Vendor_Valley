package com.group10.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.group10.Constants.IntegerConstants;
import com.group10.Service.DatabaseService;
import com.group10.Util.SqlQueries.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.group10.Enums.UserResetPasswordTableColumnOrder.*;

@Repository
public class ResetPasswordRepository {

    @Autowired
    DatabaseService databaseService;

    public boolean storeVerificationCode(int id, int code) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.insertUserResetPasswordEntry)) {
            statement.setInt(USER_ID.queryIndex, id);
            statement.setInt(VERIFICATION_CODE.queryIndex, code);
            statement.setTimestamp(CREATED_AT.queryIndex, Timestamp.valueOf(LocalDateTime.now()));
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
        try (Connection connection = databaseService.connect();
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
