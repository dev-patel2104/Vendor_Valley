package com.group10.Enums;

public enum GetUserByEmailQueryColumns {
    USER_ID("user_id"),
    LAST_NAME("last_name"),
    FIRST_NAME("first_name"),
    MOBILE("mobile"),
    IS_VENDOR("is_vendor"),
    EMAIL("email"),
    STREET("street"),
    CITY("city"),
    PROVINCE("province"),
    COUNTRY("country"),
    PASSWORD("password");

    private final String columnName;

    GetUserByEmailQueryColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
