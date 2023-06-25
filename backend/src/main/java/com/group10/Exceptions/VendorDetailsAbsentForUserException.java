package com.group10.Exceptions;

public class VendorDetailsAbsentForUserException extends RuntimeException {

    public VendorDetailsAbsentForUserException() {}

    public VendorDetailsAbsentForUserException(String message) {
        super(message);
    }

}
