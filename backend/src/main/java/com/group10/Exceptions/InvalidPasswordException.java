package com.group10.Exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String errorMessage){
        super(errorMessage);
    }
}
