package com.group10.Exceptions;

public class UserDoesntExistException extends Exception{
        public UserDoesntExistException(String errorMessage) {
        super(errorMessage);
    }
}
