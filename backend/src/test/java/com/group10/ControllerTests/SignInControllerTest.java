package com.group10.ControllerTests;

import com.group10.Controller.SignInController;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.SignUpModel;
import com.group10.Service.SignInService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInControllerTest
{
    @InjectMocks
    private SignInController signInController;

    @Mock
    private SignInService signInService;

    private SignUpModel signUpModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initializeUser();
    }

    private void initializeUser() {
        signUpModel = SignUpModel.builder().
            userId(543).
            firstName("Manu").
            lastName("Patel").
            mobile("9099929025").
            isVendor(0).
            street("111 Highpark").
            city("Toronto").
            province("Ontario").
            country("Canada").
            email("131eu@gmail.com").
            password("IDKTHEPASSWORD").
            userRole("manager").
            companyName("Dal").
            companyEmail("boon@dal.ca").
            companyRegistrationID("352523").
            companyMobile("9029895043").
            companyStreet("Clyde St").
            companyCity("Halifax").
            companyProvince("Nova Scotia").
            companyCountry("Canada").
            build();
    }


    @Test
    public void SignInSuccessful() throws UserAlreadyPresentException, SQLException {
        when(signInService.SignIn(signUpModel)).thenReturn(true);
        ResponseEntity<String> res;
        res = ResponseEntity.ok("User has been added successfully");
        assertEquals(res, signInController.signIn(signUpModel));
    }

    @Test
    public void SignIn_SQLException() throws UserAlreadyPresentException, SQLException {
        when(signInService.SignIn(signUpModel)).thenThrow(new SQLException("DBMS connection error"));
        ResponseEntity<String> res;
        res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DBMS connection error");
        assertEquals(res, signInController.signIn(signUpModel));
    }

    @Test
    public void SignIn_UserAlreadyPresentException() throws UserAlreadyPresentException, SQLException {
        when(signInService.SignIn(signUpModel)).thenThrow(new UserAlreadyPresentException("The user is already present"));
        ResponseEntity<String> res;
        res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is already present");
        assertEquals(res, signInController.signIn(signUpModel));
    }
}
