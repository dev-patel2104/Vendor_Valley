package com.group10.ExceptionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.group10.Exceptions.NoInformationFoundException;

public class NoInformationFoundExceptionTest {
    
    // Check if message is same
    @Test
    public void testNoInformationFoundExceptionMessage() {
        String errorMessage = "No information found";
        NoInformationFoundException exception = new NoInformationFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    // Check if exception is thrown and caught properly
    @Test
    public void testNoInformationFoundExceptionThrown() throws NoInformationFoundException {
        // Lambda function to call anonymous functions that are passed as arguments to methods
        assertThrows(NoInformationFoundException.class, () -> {
            throw new NoInformationFoundException("No information found");
        });
    }
}
