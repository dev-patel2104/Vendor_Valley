package com.group10.Enums;

public enum GetUserByIdQueryColumns{
    USER_ID("user_id"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    STREET("street"),
    CITY("city"),
    PROVINCE("province"),
    COUNTRY("country"),
    EMAIL("email"),
    MOBILE("mobile"),
    IS_VENDOR("is_vendor"),
    PASSWORD("password"),
    USER_ROLE("user_role"),
    COMPANY_NAME("company_name"),
    COMPANY_EMAIL("company_email"),
    COMPANY_REGISTRATION_NUMBER("company_registration_number"),
    COMPANY_MOBILE("company_mobile"),
    COMPANY_STREET("company_street"),
    COMPANY_CITY("company_city"),
    COMPANY_PROVINCE("company_province"),
    COMPANY_COUNTRY("company_country");

    private String columnName;

    GetUserByIdQueryColumns(String columnName){
        this.columnName = columnName;
    }

    public String getColumnName(){
        return columnName;
    }
}