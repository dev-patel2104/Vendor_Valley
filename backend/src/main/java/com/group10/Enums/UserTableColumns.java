package com.group10.Enums;

public enum UserTableColumns {
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

    UserTableColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
