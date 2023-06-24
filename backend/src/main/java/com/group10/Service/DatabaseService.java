package com.group10.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    private static DatabaseService dbInstance = new DatabaseService();
    private static Connection dbConnection = null;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    public static DatabaseService getInstance() {
        return dbInstance;
    }

    public Connection connect() {
        if (dbConnection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                dbConnection = DriverManager.getConnection(datasourceURL,
                        datasourceUserName,
                        datasourcePassword);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                System.out.println("Failed to connect to database: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return dbConnection;
    }
}
