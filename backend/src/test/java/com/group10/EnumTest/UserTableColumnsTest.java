package com.group10.EnumTest;

import com.group10.Enums.UserTableColumns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTableColumnsTest
{
    @Test
    public void testUserIdColumn() {
        UserTableColumns column = UserTableColumns.USER_ID;
        assertEquals("user_id", column.getColumnName());
    }

    @Test
    public void testLastNameColumn() {
        UserTableColumns column = UserTableColumns.LAST_NAME;
        assertEquals("last_name", column.getColumnName());
    }

    @Test
    public void testFirstNameColumn() {
        UserTableColumns column = UserTableColumns.FIRST_NAME;
        assertEquals("first_name", column.getColumnName());
    }

    @Test
    public void testMobileColumn() {
        UserTableColumns column = UserTableColumns.MOBILE;
        assertEquals("mobile", column.getColumnName());
    }

    @Test
    public void testIsVendorColumn() {
        UserTableColumns column = UserTableColumns.IS_VENDOR;
        assertEquals("is_vendor", column.getColumnName());
    }

    @Test
    public void testEmailColumn() {
        UserTableColumns column = UserTableColumns.EMAIL;
        assertEquals("email", column.getColumnName());
    }

    @Test
    public void testStreetColumn() {
        UserTableColumns column = UserTableColumns.STREET;
        assertEquals("street", column.getColumnName());
    }

    @Test
    public void testCityColumn() {
        UserTableColumns column = UserTableColumns.CITY;
        assertEquals("city", column.getColumnName());
    }

    @Test
    public void testProvinceColumn() {
        UserTableColumns column = UserTableColumns.PROVINCE;
        assertEquals("province", column.getColumnName());
    }

    @Test
    public void testCountryColumn() {
        UserTableColumns column = UserTableColumns.COUNTRY;
        assertEquals("country", column.getColumnName());
    }

    @Test
    public void testPasswordColumn() {
        UserTableColumns column = UserTableColumns.PASSWORD;
        assertEquals("password", column.getColumnName());
    }

    @Test
    public void testEnumValues() {
        UserTableColumns[] values = UserTableColumns.values();
        assertEquals(11, values.length);
    }

    @Test
    public void testValueOf() {
        UserTableColumns userIdColumn = UserTableColumns.valueOf("USER_ID");
        assertEquals(UserTableColumns.USER_ID, userIdColumn);

        UserTableColumns lastNameColumn = UserTableColumns.valueOf("LAST_NAME");
        assertEquals(UserTableColumns.LAST_NAME, lastNameColumn);


    }
}
