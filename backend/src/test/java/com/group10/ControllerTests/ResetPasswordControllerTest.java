package com.group10.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;

import com.group10.Controller.ResetPasswordController;
import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.PasswordsCantBeSameException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Exceptions.VerificationCodeExpiredException;
import com.group10.Model.User;
import com.group10.Service.LoginService;
import com.group10.Service.ResetPasswordService;

@SpringBootTest
public class ResetPasswordControllerTest {
    
    @MockBean 
    private ResetPasswordService resetPasswordService;
    
    @MockBean 
    private LoginService loginService;

    @Autowired
    private ResetPasswordController resetPasswordController;

	@Autowired
	private User user; 
    
    @Test
    public void testInvalidArguments_ForgotPassword(){
        String errMessage = "Invalid Arguments!";
        // Test null credentials map
        Map<String, String> credentials = null;
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test empty credentials map
        credentials = new HashMap<>();
        response = resetPasswordController.forgotPassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map without email key
        credentials = new HashMap<>();
        credentials.put("emails", "test@example.com");
        response = resetPasswordController.forgotPassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty email value
        credentials = new HashMap<>();
        credentials.put("email", "");
        response = resetPasswordController.forgotPassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDBConnectionLost_ForgotPassword() throws SQLException, UserDoesntExistException{
        String errMessage = "DB Connection Lost!";
        Mockito.doThrow(new SQLException(errMessage)).when(resetPasswordService).checkIfUserExists(anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    } 

    @Test
    public void UserDoesntExistException_forgotPassword() throws SQLException, UserDoesntExistException{
		String errorMessage = "User Doesn't Exist!";
		Mockito.doThrow(new UserDoesntExistException(errorMessage)).when(resetPasswordService).checkIfUserExists(anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

	@Test
	public void testUserDoesntExist_forgotPassword() throws SQLException, UserDoesntExistException{
		Mockito.doReturn(null).when(resetPasswordService).checkIfUserExists(anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

    @Test
    public void testMailExceptions_forgotPassword() throws Exception{
		Mockito.doReturn(user).when(resetPasswordService).checkIfUserExists(anyString());
		Mockito.doThrow(new MailSendException("test")).when(resetPasswordService).generateVerificationCode(user);
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
		assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());

		Mockito.doReturn(user).when(resetPasswordService).checkIfUserExists(anyString());
		Mockito.doThrow(new MailParseException("test")).when(resetPasswordService).generateVerificationCode(user);
        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        response = resetPasswordController.forgotPassword(credentials);
		assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());

		Mockito.doReturn(user).when(resetPasswordService).checkIfUserExists(anyString());
		Mockito.doThrow(new MailAuthenticationException("test")).when(resetPasswordService).generateVerificationCode(user);
        credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        response = resetPasswordController.forgotPassword(credentials);
		assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
    }
	
	@Test
	public void testSuccessPath_forgotPassword() throws SQLException, UserDoesntExistException{
		Mockito.doReturn(user).when(resetPasswordService).checkIfUserExists(anyString());
		Mockito.doReturn(true).when(resetPasswordService).generateVerificationCode(user);
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        ResponseEntity<String> response = resetPasswordController.forgotPassword(credentials);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
    public void testInvalidArguments_verifyConfirmationCode(){
        String errMessage = "Invalid Arguments!";
        // Test null credentials map
        Map<String, String> credentials = null;
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test empty credentials map
        credentials = new HashMap<>();
        response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map without email key
        credentials = new HashMap<>();
        credentials.put("coce", "123456");
        response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty email value
        credentials = new HashMap<>();
        credentials.put("email", "");
        response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		// Test credentials map without code key
        credentials = new HashMap<>();
        credentials.put("email", "test@gmail.com");
        response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty code value
        credentials = new HashMap<>();
        credentials.put("code", "");
        response = resetPasswordController.verifyConfirmationCode(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
    public void testDBConnectionLost_verifyConfirmationCode() throws SQLException, UserDoesntExistException, IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException{
        String errMessage = "DB Connection Lost!";
        Mockito.doThrow(new SQLException(errMessage)).when(resetPasswordService).verifyCode(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("code", "123548");
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	} 

	@Test
	public void testNoCodeInDB_verifyConfirmationCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException{
		String errMessage = "There seems to be no such information with us!";
        Mockito.doThrow(new NoInformationFoundException(errMessage)).when(resetPasswordService).verifyCode(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("code", "123548");
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testExpiredVerificationCode_verifyConfirmationCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException{
		String errMessage = "Oops! The verification code has expired!";
        Mockito.doThrow(new VerificationCodeExpiredException(errMessage)).when(resetPasswordService).verifyCode(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("code", "123548");
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testWrongCode_verifyConfirmationCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException{
		String errMessage = "You entered the wrong code, try again!";
        Mockito.doThrow(new VerificationCodeExpiredException(errMessage)).when(resetPasswordService).verifyCode(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("code", "123548");
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testSuccessPath_verifyConfirmationCode() throws IllegalArgumentException, NoInformationFoundException, VerificationCodeExpiredException, SQLException{
		String errMessage = "Success! You can now change update your password!";
        Mockito.doNothing().when(resetPasswordService).verifyCode(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("code", "123548");
        ResponseEntity<String> response = resetPasswordController.verifyConfirmationCode(credentials);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
    public void testInvalidArguments_updatePassword(){
        String errMessage = "Invalid Arguments!";
        // Test null credentials map
        Map<String, String> credentials = null;
        ResponseEntity<String> response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test empty credentials map
        credentials = new HashMap<>();
        response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map without email key
        credentials = new HashMap<>();
        credentials.put("password", "password");
        response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty email value
        credentials = new HashMap<>();
        credentials.put("email", "");
        response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		// Test credentials map without password key
        credentials = new HashMap<>();
        credentials.put("email", "test@gmail.com");
        response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Test credentials map with empty password value
        credentials = new HashMap<>();
        credentials.put("password", "");
        response = resetPasswordController.updatePassword(credentials);
        assertEquals(errMessage, response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
    public void testDBConnectionLost_updatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException{
        String errMessage = "DB Connection Lost!";
        Mockito.doThrow(new SQLException(errMessage)).when(resetPasswordService).updatePassword(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "123548");
        ResponseEntity<String> response = resetPasswordController.updatePassword(credentials);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testUserDoesntExist_udpatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException{
		String errMessage = "User Doesn't Exists!";
        Mockito.doThrow(new UserDoesntExistException(errMessage)).when(resetPasswordService).updatePassword(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<String> response = resetPasswordController.updatePassword(credentials);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testSamePassword_udpatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException{
		String errMessage = "Your new password cannot be your old password!";
        Mockito.doThrow(new PasswordsCantBeSameException(errMessage)).when(resetPasswordService).updatePassword(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<String> response = resetPasswordController.updatePassword(credentials);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

	@Test
	public void testSuccessPath_udpatePassword() throws SQLException, UserDoesntExistException, PasswordsCantBeSameException{
		String errMessage = "Successfully updated your password! You can login now with your updated password!";
        Mockito.doNothing().when(resetPasswordService).updatePassword(anyString(),anyString());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test@example.com");
        credentials.put("password", "password");
        ResponseEntity<String> response = resetPasswordController.updatePassword(credentials);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(errMessage, response.getBody());
	}

}  