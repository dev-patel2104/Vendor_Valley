package com.group10.ExceptionTests;

import com.group10.Exceptions.VendorDetailsAbsentForUserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendorDetailsAbsentForUserExceptionTest {

    @Test
    public void testVendorDetailsAbsentForUserExceptionThrown_NoArgsConstructor() {
        assertThrows(VendorDetailsAbsentForUserException.class, () -> {
            throw new VendorDetailsAbsentForUserException();
            }
        );
    }

    @Test
    public void testVendorDetailsAbsentForUserExceptionThrown_StringConstructor() {
        String vendorDetailsMissing = "User details added to database, but failed to add vendor entry";
        VendorDetailsAbsentForUserException exception = new VendorDetailsAbsentForUserException(vendorDetailsMissing);
        assertEquals(vendorDetailsMissing, exception.getMessage());
    }

}
