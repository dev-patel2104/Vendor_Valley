package com.group10.Exceptions;

/**
 * Exception thrown when a user does not exist.
 */
public class UserDoesntExistException extends Exception{
    /**
     * Constructs a new UserDoesntExistException with the specified error message.
     *
     * @param errorMessage The error message associated with the exception.
     */
    public UserDoesntExistException(String errorMessage) {
        super(errorMessage);
    }
}
