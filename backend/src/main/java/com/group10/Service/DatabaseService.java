package com.group10.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    private Connection dbConnection = null;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;


    public Connection connect() {
        try {
            dbConnection = DriverManager.getConnection(datasourceURL,
                    datasourceUserName,
                    datasourcePassword);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dbConnection;
    }
}
