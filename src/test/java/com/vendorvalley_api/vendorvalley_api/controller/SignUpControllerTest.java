package com.vendorvalley_api.vendorvalley_api.controller;

import com.vendorvalley_api.vendorvalley_api.service.SignUpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = SignUpController.class) //this annotation is used to test "only" the web layer of our application without starting a full HTTP server.
// Other available annotation is @SpringBootTest to test entire app i.e., web layer, service layer and data access layer
@ExtendWith( // to extend the behaviour of test classes and methods (annotation from jUnit only)
        SpringExtension.class // to integrate Spring TestContext framework with the jUnit's jupiter programming model.
        // src: more info on --> https://stackoverflow.com/questions/61433806/junit-5-with-spring-boot-when-to-use-extendwith-spring-or-mockito
        )
public class SignUpControllerTest {

        @Autowired
        MockMvc mockMvc; //encapsulates all our web app beans(classes with @Component, @Service, @Repository, or @Controller annotations) and makes them available for testing

        @MockBean
        SignUpService signUpService;

        @Test
        public void signUpVendorTest() throws Exception {
                mockMvc.perform(post("/signup")).andExpect(status().is2xxSuccessful());

                //service should be mocked to save a user
                signUpService.signUpVendor(signUpModel);
        }

}
