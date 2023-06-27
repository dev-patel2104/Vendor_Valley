package com.group10.Enums;

public enum UserResetPasswordTableColumnOrder {
    USER_ID(1),
    VERIFICATION_CODE(2),
    CREATED_AT(3);

    public int queryIndex;

    UserResetPasswordTableColumnOrder(int queryIndex) {
        this.queryIndex = queryIndex;
    }
}
