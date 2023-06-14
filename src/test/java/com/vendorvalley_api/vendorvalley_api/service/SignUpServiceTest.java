package com.vendorvalley_api.vendorvalley_api.service;

import com.vendorvalley_api.vendorvalley_api.model.SignUpModel;
import com.vendorvalley_api.vendorvalley_api.response.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class SignUpServiceTest {


    @MockBean
    SignUpService signUpService;

    @Test
    public void signUpVendorTest() {
        signUpService = new SignUpService();
        assertNotNull(signUpService);
        SuccessResponse successResponseExpected = new SuccessResponse("");
        SuccessResponse successResponseActual = signUpService.signUpVendor(new SignUpModel());
        assertEquals(successResponseExpected.toString(), successResponseActual.toString());
    }

}
