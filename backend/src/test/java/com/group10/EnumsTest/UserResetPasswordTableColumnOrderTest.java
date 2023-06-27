package com.group10.EnumsTest;


import com.group10.Enums.UserResetPasswordTableColumnOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResetPasswordTableColumnOrderTest {

    @Test
    public void UserResetPasswordTableColumnOrder_Test() {
        assertEquals(1, UserResetPasswordTableColumnOrder.USER_ID.queryIndex);
        assertEquals(2, UserResetPasswordTableColumnOrder.VERIFICATION_CODE.queryIndex);
        assertEquals(3, UserResetPasswordTableColumnOrder.CREATED_AT.queryIndex);
    }
}
