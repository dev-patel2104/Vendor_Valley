package com.group10.ExceptionTests;

import com.group10.Exceptions.UserAlreadyPresentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserAlreadyPresentExceptionTest
{
    @Test
    public void testUserAlreadyPresentException() {
        String message = "User with ID 12345 is already present.";
        UserAlreadyPresentException exception = new UserAlreadyPresentException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testUserAlreadyPresentExceptionWithNullMessage() {
        String message = null;
        UserAlreadyPresentException exception = new UserAlreadyPresentException(message);

        assertNull(exception.getMessage());
    }
}
