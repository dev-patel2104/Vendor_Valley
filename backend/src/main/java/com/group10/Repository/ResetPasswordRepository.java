package com.group10.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordRepository {
    
    @Value("${spring.datasource.url}")
    private String DBURL;

    @Value("${spring.datasource.username}")
    private String DBUSERNAME;

    @Value("${spring.datasource.password}")
    private String DBPASSWORD;


    public boolean storeVerificationCode(int id, int code) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            PreparedStatement statement = connection.prepareStatement("insert into user_password_reset (user_id, verification_code, created_at) values(?,?,?)");) 
        {
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
        String query = "select verification_code, created_at from user_password_reset where user_id = (select user_id from users where email = ?) order by created_at DESC limit 1";
        try (Connection connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);) 
        {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            // Row Inserted
            if (resultSet.next()) {
                int code = resultSet.getInt("verification_code");
                Timestamp created_at =  resultSet.getTimestamp("created_at");
                Timestamp sixHoursAgo = new Timestamp(System.currentTimeMillis() - 6 * 60 * 60 * 1000);
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
