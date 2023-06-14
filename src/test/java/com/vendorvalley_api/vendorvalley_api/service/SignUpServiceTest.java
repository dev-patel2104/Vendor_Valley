package com.vendorvalley_api.vendorvalley_api.service;

import com.vendorvalley_api.vendorvalley_api.model.SignUpModel;
import com.vendorvalley_api.vendorvalley_api.repository.SignUpRepository;
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


    @MockBean
    SignUpRepository signUpRepository;


    @Test
    public void signUpVendorTest() {
        signUpService = new SignUpService();
        assertNotNull(signUpService);

        SignUpModel signUpModel = new SignUpModel();
        signUpRepository.saveVendor(signUpModel);
        SuccessResponse successResponseExpected = new SuccessResponse("");
        SuccessResponse successResponseActual = signUpService.signUpVendor(new SignUpModel());
        assertEquals(successResponseExpected.toString(), successResponseActual.toString());
    }

}
