package com.group10.EnumsTest;

import com.group10.Enums.SignUpVendorSQLQueryEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignUpVendorSQLQueryEnumTest {

    @Test
    public void SignUpVendorSQLQueryEnum_Test() {
        assertEquals(1, SignUpVendorSQLQueryEnum.COMPANY_CITY.queryIndex);
        assertEquals(2, SignUpVendorSQLQueryEnum.COMPANY_COUNTRY.queryIndex);
        assertEquals(3, SignUpVendorSQLQueryEnum.COMPANY_EMAIL.queryIndex);
        assertEquals(4, SignUpVendorSQLQueryEnum.COMPANY_MOBILE.queryIndex);
        assertEquals(5, SignUpVendorSQLQueryEnum.COMPANY_NAME.queryIndex);
        assertEquals(6, SignUpVendorSQLQueryEnum.COMPANY_PROVINCE.queryIndex);
        assertEquals(7, SignUpVendorSQLQueryEnum.COMPANY_REGISTRATION_ID.queryIndex);
        assertEquals(8, SignUpVendorSQLQueryEnum.COMPANY_STREET.queryIndex);
    }
}
