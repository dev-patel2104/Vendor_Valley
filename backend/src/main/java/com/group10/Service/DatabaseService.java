package com.group10.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService { //todo: class need to be made a singleton utility and not a new object for every usage

    private static Connection dbConnection = null;

    @Value("${spring.datasource.url}")
    private String datasourceURL;

    @Value("${spring.datasource.username}")
    private String datasourceUserName;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;


    public Connection connect() {

        if (dbConnection == null) {
            try {
                dbConnection = DriverManager.getConnection(datasourceURL,
                        datasourceUserName,
                        datasourcePassword);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return dbConnection;
    }
}
