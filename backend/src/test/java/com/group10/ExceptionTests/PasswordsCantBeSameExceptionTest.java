package com.group10.ExceptionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.group10.Exceptions.PasswordsCantBeSameException;

public class PasswordsCantBeSameExceptionTest {
        // Check if message is same
    @Test
    public void testPasswordsCantBeSameExceptionMessage() {
        String errorMessage = "Passwords Cant be same";
        PasswordsCantBeSameException exception = new PasswordsCantBeSameException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    // Check if exception is thrown and caught properly
    @Test
    public void testPasswordsCantBeSameExceptionThrown() throws PasswordsCantBeSameException {
        // Lambda function to call anonymous functions that are passed as arguments to mehtods
        assertThrows(PasswordsCantBeSameException.class, () -> {
            throw new PasswordsCantBeSameException("Passwords Cant be same");
        });
    }
}
