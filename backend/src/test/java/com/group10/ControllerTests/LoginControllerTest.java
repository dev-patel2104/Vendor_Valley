package com.group10.ControllerTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.group10.Service.Interfaces.IAuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.group10.Controller.LoginController;
import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.User;

@SpringBootTest
public class LoginControllerTest {

    @Autowired
    private LoginController loginController;
    @MockBean
    private IAuthenticationService authenticationService;
    @Test
    public void successPath_login() throws UserDoesntExistException, InvalidPasswordException, SQLException{
        User user = new User();
        Mockito.doReturn(user).when(authenticationService).login(anyString(),anyString());
        
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<Map<String,String>> response = loginController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUserDoesntExist_login() throws UserDoesntExistException, InvalidPasswordException, SQLException{
        
        String errMessage = "User Doesn't Exist!";
        ResponseEntity<Map<String, String>> res;
        Map<String, String> result =  new HashMap<>();
        result.put("error", errMessage);
        res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        Mockito.doThrow(new UserDoesntExistException(errMessage)).when(authenticationService).login(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<Map<String,String>> response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    
    @Test
    public void testInvalidPassword_login() throws UserDoesntExistException, InvalidPasswordException, SQLException{
        
        String errMessage = "Invalid Password!";
        ResponseEntity<Map<String, String>> res;
        Map<String, String> result =  new HashMap<>();
        result.put("error", errMessage);
        res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        Mockito.doThrow(new InvalidPasswordException(errMessage)).when(authenticationService).login(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<Map<String,String>> response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    } 

    @Test
    public void testDBConnectionLost_login() throws UserDoesntExistException, InvalidPasswordException, SQLException{
        
        String errMessage = "DB Connection Lost!";
        ResponseEntity<Map<String, String>> res;
        Map<String, String> result =  new HashMap<>();
        result.put("error", errMessage);
        res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        Mockito.doThrow(new InvalidPasswordException(errMessage)).when(authenticationService).login(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<Map<String,String>> response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    } 

    @Test
    public void testInvalidArguments_login() {
        String errMessage = "Invalid Arguments!";
        ResponseEntity<Map<String, String>> res;
        Map<String, String> result =  new HashMap<>();
        result.put("error", errMessage);
        res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

        // Test null credentials map
        Map<String, String> credentials = null;
        ResponseEntity<Map<String,String>> response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Test empty credentials map
        credentials = new HashMap<>();
        response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map without email key
        credentials = new HashMap<>();
        credentials.put("password", "password");
        response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map without password key
        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty email value
        credentials = new HashMap<>();
        credentials.put("email", "");
        credentials.put("password", "password");
        response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty password value
        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "");
        response = loginController.login(credentials);
        assertEquals(res, response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
    }
}
