package com.group10.Exceptions;

/**
 * Exception thrown when an invalid password is encountered.
 */
public class InvalidPasswordException extends Exception {
    /**
     * Constructs a new InvalidPasswordException with the specified error message.
     *
     * @param errorMessage The error message associated with the exception.
     */
    public InvalidPasswordException(String errorMessage){
        super(errorMessage);
    }
}
