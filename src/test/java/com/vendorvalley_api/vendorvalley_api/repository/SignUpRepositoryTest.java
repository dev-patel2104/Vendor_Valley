package com.vendorvalley_api.vendorvalley_api.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
public class SignUpRepositoryTest {

    @MockBean
    SignUpRepository signUpRepository;

    @Test
    public void saveVendorTest() {
        assertNotNull(signUpRepository);
    }


}
