package com.group10.Exceptions;

/**
 * Exception thrown when a verification code has expired.
 */
public class VerificationCodeExpiredException extends Exception{
    /**
     * Constructs a new VerificationCodeExpiredException with the specified detail message.
     *
     * @param message The detail message for the exception
     */
    public VerificationCodeExpiredException(String message){
        super(message);
    }
}
