package com.group10.ExceptionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.group10.Exceptions.InvalidPasswordException;

public class InvalidPasswordExceptionTest {
    
    // Check if message is same
    @Test
    public void testInvalidPasswordExceptionMessage() {
        String errorMessage = "Invalid password";
        InvalidPasswordException exception = new InvalidPasswordException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    // Check if exception is thrown and caught properly
    @Test
    public void testInvalidPasswordExceptionThrown() throws InvalidPasswordException {
        // Lambda function to call anonymous functions that are passed as arguments to mehtods
        assertThrows(InvalidPasswordException.class, () -> {
            throw new InvalidPasswordException("Invalid password");
        });
    }
}
