package com.group10.Exceptions;

public class UserAlreadyPresentException extends Exception
{
    public UserAlreadyPresentException(String message)
    {
        super(message);
    }
}
