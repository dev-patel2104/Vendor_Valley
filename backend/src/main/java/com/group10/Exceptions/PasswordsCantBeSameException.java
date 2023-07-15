package com.group10.Exceptions;

/**
 * Exception thrown when two passwords are found to be the same.
 */
public class PasswordsCantBeSameException extends Exception{
    /**
     * Constructs a new PasswordsCantBeSameException with the specified detail message.
     *
     * @param message the detail message
     */
    public PasswordsCantBeSameException(String message){
        super(message);
    }
}
