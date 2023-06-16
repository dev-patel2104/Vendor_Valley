package com.vendorvalley_api.vendorvalley_api.sqlQueries;

public class SQLQuery {

    public static final String insertVendorQuery = "INSERT INTO vendor_valley.vendor_model (company_city, company_country, company_email, company_mobile, company_name, company_province, company_registration_number, company_street) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
}
