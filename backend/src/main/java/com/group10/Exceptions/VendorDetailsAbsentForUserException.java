package com.group10.Exceptions;

/**
 * Custom exception class indicating that vendor details are absent for a user.
 * This exception is typically thrown when attempting to retrieve vendor-related information
 * for a user who is not registered as a vendor.
 */
public class VendorDetailsAbsentForUserException extends RuntimeException {

    /**
     * Constructs a new VendorDetailsAbsentForUserException with no detail message.
     */
    public VendorDetailsAbsentForUserException() {}

    /**
     * Constructs a new VendorDetailsAbsentForUserException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public VendorDetailsAbsentForUserException(String message) {
        super(message);
    }

}
