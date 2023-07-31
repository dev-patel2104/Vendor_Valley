package com.group10.EnumTest;

import com.group10.Enums.SearchServiceQueryColumns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchServiceQueryColumnsTest
{
    @Test
    public void testServiceIdColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.SERVICE_ID;
        assertEquals("service_id", column.getColumnName());
    }

    @Test
    public void testUserIdColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.USER_ID;
        assertEquals("user_id", column.getColumnName());
    }

    @Test
    public void testServiceNameColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.SERVICE_NAME;
        assertEquals("service_name", column.getColumnName());
    }

    @Test
    public void testServiceDescriptionColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.SERVICE_DESCRIPTION;
        assertEquals("service_description", column.getColumnName());
    }

    @Test
    public void testServicePriceColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.SERVICE_PRICE;
        assertEquals("service_price", column.getColumnName());
    }

    @Test
    public void testCategoriesColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.CATEGORIES;
        assertEquals("categories", column.getColumnName());
    }

    @Test
    public void testCompanyStreetColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.COMPANY_STREET;
        assertEquals("company_street", column.getColumnName());
    }

    @Test
    public void testCompanyCityColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.COMPANY_CITY;
        assertEquals("company_city", column.getColumnName());
    }

    @Test
    public void testCompanyProvinceColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.COMPANY_PROVINCE;
        assertEquals("company_province", column.getColumnName());
    }

    @Test
    public void testCompanyCountryColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.COMPANY_COUNTRY;
        assertEquals("company_country", column.getColumnName());
    }

    @Test
    public void testAverageRatingColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.AVERAGE_RATING;
        assertEquals("avgRating", column.getColumnName());
    }

    @Test
    public void testTotalBookingsColumn() {
        SearchServiceQueryColumns column = SearchServiceQueryColumns.TOTAL_BOOKINGS;
        assertEquals("totalBookings", column.getColumnName());
    }

    @Test
    public void testEnumValues() {
        SearchServiceQueryColumns[] values = SearchServiceQueryColumns.values();
        assertEquals(12, values.length);
    }

    @Test
    public void testValueOf() {
        SearchServiceQueryColumns serviceIdColumn = SearchServiceQueryColumns.valueOf("SERVICE_ID");
        assertEquals(SearchServiceQueryColumns.SERVICE_ID, serviceIdColumn);

        SearchServiceQueryColumns userIdColumn = SearchServiceQueryColumns.valueOf("USER_ID");
        assertEquals(SearchServiceQueryColumns.USER_ID, userIdColumn);

    }
}
