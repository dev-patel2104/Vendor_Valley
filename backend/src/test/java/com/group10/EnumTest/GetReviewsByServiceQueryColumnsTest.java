package com.group10.EnumTest;

import com.group10.Enums.GetReviewsByServiceQueryColumns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetReviewsByServiceQueryColumnsTest
{
    @Test
    public void testServiceIdColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.SERVICE_ID;
        assertEquals("service_id", column.getColumnName());
    }

    @Test
    public void testUserIdColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.USER_ID;
        assertEquals("user_id", column.getColumnName());
    }

    @Test
    public void testBookingIdColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.BOOKING_ID;
        assertEquals("booking_id", column.getColumnName());
    }

    @Test
    public void testTitleColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.TITLE;
        assertEquals("title", column.getColumnName());
    }

    @Test
    public void testCommentTextColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.COMMENT_TEXT;
        assertEquals("comment_text", column.getColumnName());
    }

    @Test
    public void testReviewDateColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.REVIEW_DATE;
        assertEquals("review_date", column.getColumnName());
    }

    @Test
    public void testRatingColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.RATING;
        assertEquals("rating", column.getColumnName());
    }

    @Test
    public void testNameColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.NAME;
        assertEquals("name", column.getColumnName());
    }

    @Test
    public void testCityColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.CITY;
        assertEquals("city", column.getColumnName());
    }

    @Test
    public void testCountryColumn() {
        GetReviewsByServiceQueryColumns column = GetReviewsByServiceQueryColumns.COUNTRY;
        assertEquals("country", column.getColumnName());
    }

    @Test
    public void testEnumValues() {
        GetReviewsByServiceQueryColumns[] values = GetReviewsByServiceQueryColumns.values();
        assertEquals(10, values.length);
        assertEquals(GetReviewsByServiceQueryColumns.SERVICE_ID, values[0]);
        assertEquals(GetReviewsByServiceQueryColumns.USER_ID, values[1]);
        assertEquals(GetReviewsByServiceQueryColumns.BOOKING_ID, values[2]);
        assertEquals(GetReviewsByServiceQueryColumns.TITLE, values[3]);
        assertEquals(GetReviewsByServiceQueryColumns.COMMENT_TEXT, values[4]);
        assertEquals(GetReviewsByServiceQueryColumns.REVIEW_DATE, values[5]);
        assertEquals(GetReviewsByServiceQueryColumns.RATING, values[6]);
        assertEquals(GetReviewsByServiceQueryColumns.NAME, values[7]);
        assertEquals(GetReviewsByServiceQueryColumns.CITY, values[8]);
        assertEquals(GetReviewsByServiceQueryColumns.COUNTRY, values[9]);
    }

    @Test
    public void testValueOf() {
        GetReviewsByServiceQueryColumns serviceIdColumn = GetReviewsByServiceQueryColumns.valueOf("SERVICE_ID");
        assertEquals(GetReviewsByServiceQueryColumns.SERVICE_ID, serviceIdColumn);

        GetReviewsByServiceQueryColumns userIdColumn = GetReviewsByServiceQueryColumns.valueOf("USER_ID");
        assertEquals(GetReviewsByServiceQueryColumns.USER_ID, userIdColumn);

    }
}
