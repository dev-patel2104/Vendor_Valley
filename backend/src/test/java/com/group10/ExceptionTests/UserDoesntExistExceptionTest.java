package com.group10.ExceptionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.group10.Exceptions.UserDoesntExistException;

public class UserDoesntExistExceptionTest {
        // Check if message is same
    @Test
    public void testUserDoesntExistMessage() {
        String errorMessage = "Invalid password";
        UserDoesntExistException exception = new UserDoesntExistException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    // Check if exception is thrown and caught properly
    @Test
    public void testUserDoesntExistExceptionThrown() throws UserDoesntExistException {
        // Lambda function to call anonymous functions that are passed as arguments to mehtods
        assertThrows(UserDoesntExistException.class, () -> {
            throw new UserDoesntExistException("Invalid password");
        });
    }
}
