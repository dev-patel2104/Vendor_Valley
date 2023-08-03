package com.group10.EnumTest;

import com.group10.Enums.GetServiceDetailsQueryColumns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetServiceDetailsQueryColumnsTest
{
    @Test
    public void testServiceIdColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.SERVICE_ID;
        assertEquals("service_id", column.getColumnName());
    }

    @Test
    public void testUserIdColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.USER_ID;
        assertEquals("user_id", column.getColumnName());
    }

    @Test
    public void testServiceNameColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.SERVICE_NAME;
        assertEquals("service_name", column.getColumnName());
    }

    @Test
    public void testServiceDescriptionColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.SERVICE_DESCRIPTION;
        assertEquals("service_description", column.getColumnName());
    }

    @Test
    public void testServicePriceColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.SERVICE_PRICE;
        assertEquals("service_price", column.getColumnName());
    }

    @Test
    public void testCompanyEmailColumn() {
        GetServiceDetailsQueryColumns column = GetServiceDetailsQueryColumns.COMPANY_EMAIL;
        assertEquals("company_email", column.getColumnName());
    }

    @Test
    public void testEnumValues() {
        GetServiceDetailsQueryColumns[] values = GetServiceDetailsQueryColumns.values();
        assertEquals(6, values.length);
        assertEquals(GetServiceDetailsQueryColumns.SERVICE_ID, values[0]);
        assertEquals(GetServiceDetailsQueryColumns.USER_ID, values[1]);
        assertEquals(GetServiceDetailsQueryColumns.SERVICE_NAME, values[2]);
        assertEquals(GetServiceDetailsQueryColumns.SERVICE_DESCRIPTION, values[3]);
        assertEquals(GetServiceDetailsQueryColumns.SERVICE_PRICE, values[4]);
        assertEquals(GetServiceDetailsQueryColumns.COMPANY_EMAIL, values[5]);
    }

    @Test
    public void testValueOf() {
        GetServiceDetailsQueryColumns serviceIdColumn = GetServiceDetailsQueryColumns.valueOf("SERVICE_ID");
        assertEquals(GetServiceDetailsQueryColumns.SERVICE_ID, serviceIdColumn);

        GetServiceDetailsQueryColumns userIdColumn = GetServiceDetailsQueryColumns.valueOf("USER_ID");
        assertEquals(GetServiceDetailsQueryColumns.USER_ID, userIdColumn);

    }
}
