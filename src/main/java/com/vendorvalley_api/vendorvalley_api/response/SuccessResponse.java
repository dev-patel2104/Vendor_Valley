package com.vendorvalley_api.vendorvalley_api.response;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
