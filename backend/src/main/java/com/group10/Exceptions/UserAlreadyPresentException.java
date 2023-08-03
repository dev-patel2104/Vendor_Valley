package com.group10.Exceptions;

/**
 * Exception thrown when attempting to add a user that is already present.
 */
public class UserAlreadyPresentException extends Exception
{
    /**
     * Constructs a new UserAlreadyPresentException with the specified detail message.
     *
     * @param message The detail message for this exception.
     */
    public UserAlreadyPresentException(String message)
    {
        super(message);
    }
}
