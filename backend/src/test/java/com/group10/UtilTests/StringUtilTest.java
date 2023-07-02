package com.group10.UtilTests;

import com.group10.Util.StringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest {

    @Test
    public void isNotNullAndNotEmptyTest() {
        String nullStr = null;
        String emptyStr = "";
        String nameStr = "dalhousie";

        assertFalse(StringUtil.isNotNullAndNotEmpty(nullStr));
        assertFalse(StringUtil.isNotNullAndNotEmpty(emptyStr));

        assertTrue(StringUtil.isNotNullAndNotEmpty(nameStr));
    }
}
