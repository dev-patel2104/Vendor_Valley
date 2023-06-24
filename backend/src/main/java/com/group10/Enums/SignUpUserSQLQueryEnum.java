package com.group10.Enums;

public enum SignUpUserSQLQueryEnum {
    USER_FIRSTNAME(1),
    USER_LASTNAME(2),
    USER_STREET(3),
    USER_CITY(4),
    USER_PROVINCE(5),
    USER_COUNTRY(6),
    USER_EMAIL(7),
    USER_MOBILE(8),
    USER_IS_VENDOR(9),
    USER_PASSWORD(10),
    USER_ID(11);

    public int queryIndex;

    SignUpUserSQLQueryEnum(int queryIndex) {
        this.queryIndex = queryIndex;
    }
}
