package com.group10.Exceptions;
/**
 * Custom exception class to indicate that no information was found.
 * This exception is typically thrown when a search or query operation
 * does not yield any results or data.
 */
public class NoInformationFoundException extends Exception {

    /**
     * Constructs a new NoInformationFoundException with the specified detail message.
     *
     * @param message The detail message, which should describe the reason for the exception.
     */
    public NoInformationFoundException(String message) {
        super(message);
    }
}
