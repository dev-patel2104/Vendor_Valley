package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.junit.jupiter.api.Test;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Review;
import com.group10.Model.Service;
import com.group10.Model.User;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.UserRepository;
import com.group10.Service.CustomerSelectsVendorService;
import com.group10.Util.EmailUtil;
import com.group10.Util.JWTTokenHandler;

@SpringBootTest
public class CustomerSelectsVendorServiceTest {

    @MockBean
    private ServiceRepository serviceRepository;

    @MockBean
    private JWTTokenHandler jwtTokenHandler;

    @MockBean
    private EmailUtil emailUtil;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CustomerSelectsVendorService customerSelectsVendorService;

    @Autowired
    private User user;

    @Autowired
    private Service service;


    @Test
    public void testEmptyListReturn_getReviews() throws Exception {
        // Mock repository layer and check if the correct results are returned
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(new ArrayList<>()).when(serviceRepository).getReviewsForService(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testNonEmptyListReturn_getReviews() throws Exception {
        // Mock repository layer and check if the correct results are returned
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(new ArrayList<>()).when(serviceRepository).getReviewsForService(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testServiceExists_getReviews() throws Exception{
        // Mock repository layer and check if the correct results are returned 
        boolean serviceExists = true;
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(serviceExists).when(serviceRepository).checkIfServiceExists(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testServiceDoesntExists_getReviews() throws Exception{
        // Mock repository layer and check if the correct results are returned 
        boolean serviceExists = false;
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(serviceExists).when(serviceRepository).checkIfServiceExists(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testSuccessPath_writeReviews() throws Exception{
        boolean success = true;
        Review review = new Review();
        String token = "token";
        int fakeUserId = 999999999;
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("userId")).thenReturn(claim);
        Mockito.when(claim.asInt()).thenReturn(fakeUserId);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(success).when(serviceRepository).writeReviews(Mockito.any());
        assertEquals(true, customerSelectsVendorService.writeReviews(review, token));
    }

    @Test
    public void testFailurePath_writeReviews() throws Exception{
        boolean success = false;
        Review review = new Review();
        String token = "token";
        int fakeUserId = 999999999;
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("userId")).thenReturn(claim);
        Mockito.when(claim.asInt()).thenReturn(fakeUserId);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(success).when(serviceRepository).writeReviews(Mockito.any());
        assertEquals(false, customerSelectsVendorService.writeReviews(review, token));
    }

    @Test
    public void testInvalidUserID_writeReviews() throws Exception{
        Review review = new Review();
        String token = "token";
        Mockito.doThrow(new JWTVerificationException("Invalid Token!")).when(serviceRepository).writeReviews(Mockito.any());
        assertThrows(Exception.class, () -> customerSelectsVendorService.writeReviews(review, token));
    }

    @Test
    public void testSQLException_writeReviews() throws Exception{
        Review review = new Review();
        String token = "token";
        Mockito.doThrow(new SQLException("SQL Exception!")).when(serviceRepository).writeReviews(Mockito.any());
        assertThrows(Exception.class, () -> customerSelectsVendorService.writeReviews(review, token));
    }

    @Test
    public void testSuccessPath_sendEmail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doReturn(service).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        Mockito.doReturn(true).when(emailUtil).sendSimpleMail(Mockito.any());
        assertTrue(customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testFailurePath_sendEmail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doReturn(service).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        Mockito.doReturn(false).when(emailUtil).sendSimpleMail(Mockito.any());
        assertFalse(customerSelectsVendorService.sendEmail(serviceId, emailText, token));        
    }

    @Test
    public void testInvalidUserID_sendEmail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doThrow(new JWTVerificationException("Invalid Token!")).when(jwtTokenHandler).decodeJWTToken(token);
        assertThrows(JWTVerificationException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testFindByEmailFailure_sendEmail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doThrow(new SQLException("Database Connection Lost")).when(userRepository).findByEmail(fakeUserEmail);
        assertThrows(SQLException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testInvalidServiceID_sendEmail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doThrow(new SQLException("Database Connection Lost!")).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        assertThrows(SQLException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testMailAuthenticationException_sendMail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doReturn(service).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        Mockito.doThrow(new MailAuthenticationException("Mail authentication failed!")).when(emailUtil).sendSimpleMail(Mockito.any());
        assertThrows(MailAuthenticationException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testMailSendException_sendMail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doReturn(service).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        Mockito.doThrow(new MailSendException("Mail authentication failed!")).when(emailUtil).sendSimpleMail(Mockito.any());
        assertThrows(MailSendException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

    @Test
    public void testMailParseException_sendMail() throws Exception{
        String serviceId = "2";
        String emailText = "text";
        String token = "token";
        String fakeUserEmail = "Test@test.com";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(decodedJWT.getClaim("email")).thenReturn(claim);
        Mockito.when(claim.asString()).thenReturn(fakeUserEmail);
        Mockito.doReturn(decodedJWT).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(user).when(userRepository).findByEmail(fakeUserEmail);
        Mockito.doReturn(service).when(serviceRepository).getServiceDetails(Mockito.anyInt());
        Mockito.doThrow(new MailParseException("Mail authentication failed!")).when(emailUtil).sendSimpleMail(Mockito.any());
        assertThrows(MailParseException.class, () -> customerSelectsVendorService.sendEmail(serviceId, emailText, token));
    }

}
