package com.group10.Exceptions;

public class PasswordsCantBeSameException extends Exception{
    public PasswordsCantBeSameException(String message){
        super(message);
    }
}
