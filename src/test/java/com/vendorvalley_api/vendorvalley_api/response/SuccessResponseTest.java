package com.vendorvalley_api.vendorvalley_api.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuccessResponseTest {

    @Test
    public void SuccessResponse_Constructor_Test() {
        SuccessResponse successResponse = new SuccessResponse("Test response message");
        assertEquals("Test response message", successResponse.toString());
    }
}
