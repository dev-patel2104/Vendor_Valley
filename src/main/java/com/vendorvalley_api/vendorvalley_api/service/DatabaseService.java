package com.vendorvalley_api.vendorvalley_api.service;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    private static Connection dbConnection = null;

//    @Value("${spring.datasource.url}") //TODO: change it to get from prop file
    private static String datasourceURL = "jdbc:mysql://localhost:3306/vendor_valley?serverTimezone=UTC";

//    @Value("${spring.datasource.username}")
    private static String datasourceUserName = "root";

//    @Value("${spring.datasource.password}")
    private static String datasourcePassword = "JCrex@ad777";


    public static Connection connect() {

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
