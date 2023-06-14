package com.vendorvalley_api.vendorvalley_api.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@WebMvcTest(controllers = SignUpController.class) //this annotation is used to test only the web layer of our application.
// Other available annotation is @SpringBootTest to test entire app i.e., web layer, service layer and data access layer
@ExtendWith( // to extend the behaviour of test classes and methods (annotation from jUnit only)
        SpringExtension.class // to integrate Spring TestContext framework with the jUnit's jupiter programming model.
        // src: more info on --> https://stackoverflow.com/questions/61433806/junit-5-with-spring-boot-when-to-use-extendwith-spring-or-mockito
        )
public class SignUpControllerTest {




}
