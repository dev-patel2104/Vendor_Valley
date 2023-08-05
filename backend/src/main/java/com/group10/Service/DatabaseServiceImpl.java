package com.group10.Service;

import com.group10.Service.Interfaces.IDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@Slf4j
public class DatabaseServiceImpl implements IDatabaseService {

    private Connection dbConnection = null;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;


    /**
     * Establishes a connection to the database.
     *
     * @return The database connection.
     * @throws RuntimeException If there is an issue connecting to the database.
     */
    @Override
    public Connection connect() {
        try {
            dbConnection = DriverManager.getConnection(datasourceURL,
                    datasourceUserName,
                    datasourcePassword);

            log.info("Database connection established.");

        } catch (SQLException e) {
            log.error("Failed to connect to database: {}", e.getMessage());
            throw new RuntimeException("Failed to connect to database: " + e.getMessage());
        }
        return dbConnection;
    }
}
