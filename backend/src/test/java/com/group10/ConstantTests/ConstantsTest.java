package com.group10.ConstantTests;

import com.group10.Constants.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConstantsTest
{
    @Test
    public void sixHoursInMilliSecondTest()
    {
        Integer seconds = 21600000;
        assertEquals(seconds,Constants.SIXHOURSINMILLISECONDS);
    }
    @Test
    public void testVerificationCodeExpired() {
        assertEquals(0, Constants.VERIFICATIONCODEEXPIRED);
    }

    @Test
    public void testRowNotInserted() {
        assertEquals(-1, Constants.ROWNOTINSERTED);
    }

    @Test
    public void testUserAlreadyExists() {
        assertEquals(-2, Constants.USERALREADYEXISTS);
    }

    @Test
    public void testUserNotInserted() {
        assertEquals(-3, Constants.USERNOTINSERTED);
    }

    @Test
    public void testUserDoesNotExist() {
        assertEquals(-4, Constants.USERDOESNTEXIST);
    }

    @Test
    public void testPriceConstant() {
       assertEquals("price", Constants.PRICE);
    }

    @Test
    public void testRatingConstant() {
        assertEquals("ratings", Constants.RATING);
    }

    @Test
    public void testBookingsConstant() {
        assertEquals("bookings", Constants.BOOKINGS);
    }

    @Test
    public void testCategoryConstant() {
        assertEquals("category", Constants.CATEGORY);
    }

    @Test
    public void testAscConstant() {
        assertTrue(Constants.ASC);
    }

    @Test
    public void testDescConstant() {
        assertFalse(Constants.DESC);
    }
}
