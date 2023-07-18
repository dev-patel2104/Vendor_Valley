package com.group10.ConstantTests;

import com.group10.Constants.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegerConstantsTest
{
    @Test
    public void sixHoursInMilliSecondTest()
    {
        Integer seconds = 21600000;
        assertEquals(seconds,Constants.SIXHOURSINMILLISECONDS);
    }
}
