package com.group10.Util.SqlQueries;

public class SQLQuery {

    public static final String insertVendorQuery = "INSERT INTO vendor_valley.vendor_model (company_city, company_country, company_email, company_mobile, company_name, company_province, company_registration_number, company_street) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String addUserQuery = "INSERT INTO users (first_name, last_name, street, city, " +
            "province, country, email, mobile, is_vendor, Password)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String getAllUsers = "SELECT * FROM users WHERE email = ?";
    public static final String updateUserQuery = "UPDATE users SET first_name = ?, last_name = ?, street = ?, city = ?, province = ?, country = ?, email = ?, mobile = ?, is_vendor = ?, password = ?  WHERE (user_id = ?)";
}
