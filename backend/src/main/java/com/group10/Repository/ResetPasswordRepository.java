package com.group10.Repository;

import java.sql.*;
import java.time.LocalDateTime;

import com.group10.Constants.Constants;
import com.group10.Repository.Interfaces.IResetPasswordRepository;
import com.group10.Service.Interfaces.IDatabaseService;
import com.group10.Util.SqlQueries.SQLQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



/**
 * Repository class for storing and retrieving reset password verification codes in the database.
 */
@Repository
@Slf4j
public class ResetPasswordRepository implements IResetPasswordRepository {

    @Autowired
    IDatabaseService databaseService;


    /**
     * Stores a verification code for password reset in the database.
     *
     * @param id   The ID of the user for whom to store the verification code.
     * @param code The verification code to store.
     * @return True if the verification code is successfully stored, false otherwise.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public boolean storeVerificationCode(int id, int code) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement insertUserPreparedStatement = connection.prepareStatement(SQLQueries.insertUserResetPasswordEntry)) {

            insertUserPreparedStatement.setInt(1, id);
            insertUserPreparedStatement.setInt(2, code);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            insertUserPreparedStatement.setTimestamp(3, now);

            int affectedRows = insertUserPreparedStatement.executeUpdate();

            if (affectedRows > 0) {
                log.info("Verification code stored for user ID: {}", id);
                return true;
            } else {
                log.warn("Failed to store verification code for user ID: {}", id);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error while storing verification code for user ID {}: {}", id, e.getMessage());
            throw new SQLException("Error while storing verification code", e);
        }
    }


    /**
     * Retrieves the verification code associated with a user's password reset request.
     *
     * @param email The email address of the user.
     * @return The verification code if found, or specific constants if not found or expired.
     * @throws SQLException If an error occurs while interacting with the database.
     */
    public int getVerificationCode(String email) throws SQLException {
        try (Connection connection = databaseService.connect();
             PreparedStatement getResetPasswordInfoPreparedStatement = connection.prepareStatement(SQLQueries.getPasswordRestInfoByUserId)) {
            getResetPasswordInfoPreparedStatement.setString(1, email);
            ResultSet resetPasswordInfoResultSet = getResetPasswordInfoPreparedStatement.executeQuery();

            if (resetPasswordInfoResultSet.next()) {
                int code = resetPasswordInfoResultSet.getInt("verification_code");
                Timestamp createdAt = resetPasswordInfoResultSet.getTimestamp("created_at");
                Timestamp sixHoursAgo = new Timestamp(System.currentTimeMillis() - Constants.SIXHOURSINMILLISECONDS);

                if (createdAt.before(sixHoursAgo)) {
                    log.warn("Verification code for email {} has expired", email);
                    return Constants.VERIFICATIONCODEEXPIRED;
                }

                log.info("Retrieved verification code for email");
                return code;
            } else {
                log.warn("Verification code not found for email: {}", email);
                return Constants.ROWNOTINSERTED;
            }
        } catch (SQLException e) {
            log.error("Error while retrieving verification code for email {}: {}", email, e.getMessage());
            throw new SQLException("Error while retrieving verification code", e);
        }
    }

}
