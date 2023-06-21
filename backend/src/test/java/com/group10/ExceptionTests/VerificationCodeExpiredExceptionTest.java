package com.group10.ExceptionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.group10.Exceptions.VerificationCodeExpiredException;

public class VerificationCodeExpiredExceptionTest {
    
    // Check if message is same
    @Test
    public void testVerificationCodeExpiredExceptionMessage() {
        String errorMessage = "Invalid password";
        VerificationCodeExpiredException exception = new VerificationCodeExpiredException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    // Check if exception is thrown and caught properly
    @Test
    public void testVerificationCodeExpiredExceptionThrown() throws VerificationCodeExpiredException {
        // Lambda function to call anonymous functions that are passed as arguments to mehtods
        assertThrows(VerificationCodeExpiredException.class, () -> {
            throw new VerificationCodeExpiredException("Invalid password");
        });
    }
}
