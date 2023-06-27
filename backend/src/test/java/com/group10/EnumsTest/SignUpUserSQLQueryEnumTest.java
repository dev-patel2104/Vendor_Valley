package com.group10.EnumsTest;

import com.group10.Enums.SignUpUserSQLQueryEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpUserSQLQueryEnumTest {

    @Test
    public void SignUpUserSQLQueryEnum_Test() {
        assertEquals(1, SignUpUserSQLQueryEnum.USER_FIRSTNAME.queryIndex);
        assertEquals(2, SignUpUserSQLQueryEnum.USER_LASTNAME.queryIndex);
        assertEquals(3, SignUpUserSQLQueryEnum.USER_STREET.queryIndex);
        assertEquals(4, SignUpUserSQLQueryEnum.USER_CITY.queryIndex);
        assertEquals(5, SignUpUserSQLQueryEnum.USER_PROVINCE.queryIndex);
        assertEquals(6, SignUpUserSQLQueryEnum.USER_COUNTRY.queryIndex);
        assertEquals(7, SignUpUserSQLQueryEnum.USER_EMAIL.queryIndex);
        assertEquals(8, SignUpUserSQLQueryEnum.USER_MOBILE.queryIndex);
        assertEquals(9, SignUpUserSQLQueryEnum.USER_IS_VENDOR.queryIndex);
        assertEquals(10, SignUpUserSQLQueryEnum.USER_PASSWORD.queryIndex);
        assertEquals(11, SignUpUserSQLQueryEnum.USER_ID.queryIndex);
    }

}
