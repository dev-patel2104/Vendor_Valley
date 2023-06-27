package com.group10.Util.SqlQueries;

public class SQLQuery {

    public static final String insertVendorQuery = "INSERT INTO vendors (user_id, user_role, company_city, company_country, company_email, company_mobile, company_name, company_province, company_registration_number, company_street) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String addUserQuery = "INSERT INTO users (first_name, last_name, street, city, " +
            "province, country, email, mobile, is_vendor, Password)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String BeginTransaction = "Begin transaction;";
    public static final String EndTransaction = "End transaction;";
    public static final String getUserByEmailID = "SELECT * FROM users WHERE email = ?";
    public static final String updateUserQuery = "UPDATE users SET first_name = ?, last_name = ?, street = ?, city = ?, province = ?, country = ?, email = ?, mobile = ?, is_vendor = ?, password = ?  WHERE (user_id = ?)";
    public static final String getPasswordRestInfoByUserId = "select verification_code, created_at from user_password_reset where user_id = (select user_id from users where email = ?) order by created_at DESC limit 1";
    public static final String insertUserResetPasswordEntry = "insert into user_password_reset (user_id, verification_code, created_at) values(?,?,?)";
}
