package com.group10.ControllerTests;

import com.group10.Controller.SignInController;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.User;
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

    private User user = new User();
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void intializeUser()
    {
        user.setFirstName("Manu");
        user.setLastName("Patel");
        user.setMobile("9099929025");
        user.setVendor(0);
        user.setStreet("111 Highpark");
        user.setCity("Toronto");
        user.setProvince("ON");
        user.setCountry("Canada");
        user.setEmail("131eu@gmail.com");
        user.setPassword("IDKTHEPASSWORD");
    }
    @Test
    public void SignInSuccessfull() throws Exception
    {
        intializeUser();
        when(signInService.SignIn(user)).thenReturn(true);
        ResponseEntity<String> res;
        res = ResponseEntity.ok("User has been added successfully");
        assertEquals(res, signInController.signIn(user));
    }

    @Test
    public void SignIn_SQLException() throws  Exception
    {
        intializeUser();
        when(signInService.SignIn(user)).thenThrow(new SQLException("DBMS connection error"));
        ResponseEntity<String> res;
        res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DBMS connection error");
        assertEquals(res, signInController.signIn(user));

    }

    @Test
    public void SignIn_UserAlreadyPresentException() throws  Exception
    {
        intializeUser();
        when(signInService.SignIn(user)).thenThrow(new UserAlreadyPresentException("The user is already present"));
        ResponseEntity<String> res;
        res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is already present");
        assertEquals(res, signInController.signIn(user));

    }

}
