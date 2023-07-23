package com.group10.Repository;

import java.sql.*;
import java.time.LocalDateTime;

import com.group10.Constants.Constants;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



/**
 * Repository class for storing and retrieving reset password verification codes in the database.
 */
@Repository
public class ResetPasswordRepository {

    @Autowired
    IDatabaseService databaseService;

    /**
     * Stores a verification code for a user in the database.
     *
     * @param id The user ID
     * @param code The verification code
     * @return true if the verification code was successfully stored, false otherwise
     * @throws SQLException if there is an error with the database connection
     */
    public boolean storeVerificationCode(int id, int code) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.insertUserResetPasswordEntry)) {
            statement.setInt(1, id);
            statement.setInt(2, code);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(3, now);
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

    /**
     * Retrieves the verification code associated with the given email from the database.
     *
     * @param email The email for which to retrieve the verification code.
     * @return The verification code, or -1 if no code is found.
     * @throws SQLException If there is an error connecting to the database.
     */
    public int getVerificationCode(String email) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement statement = connection.prepareStatement(SQLQueries.getPasswordRestInfoByUserId)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            // Row Inserted
            if (resultSet.next()) {
                int code = resultSet.getInt("verification_code");
                Timestamp created_at =  resultSet.getTimestamp("created_at");
                Timestamp sixHoursAgo = new Timestamp(System.currentTimeMillis() - Constants.SIXHOURSINMILLISECONDS);
                if (created_at.before(sixHoursAgo)) {
                    // created_at is older than 6 hours
                    return Constants.VERIFICATIONCODEEXPIRED;
                }
                return code;
            } else {
                // row not inserted
                return Constants.ROWNOTINSERTED;
            }
        } 
        catch (SQLException e) {
            // Handle exception
            throw new SQLException("Database Connection is lost!");
        }
    } 
}
