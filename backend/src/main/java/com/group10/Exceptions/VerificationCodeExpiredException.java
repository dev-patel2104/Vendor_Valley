package com.group10.Exceptions;

public class VerificationCodeExpiredException extends Exception{
    public VerificationCodeExpiredException(String message){
        super(message);
    }
}
