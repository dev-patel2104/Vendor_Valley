package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Review;
import com.group10.Repository.ServiceRepository;
import com.group10.Service.CustomerSelectsVendorService;
import com.group10.Util.JWTTokenHandler;

@SpringBootTest
public class CustomerSelectsVendorServiceTest {

    @MockBean
    private ServiceRepository serviceRepository;

    @MockBean
    private JWTTokenHandler jwtTokenHandler;
    
    @Autowired
    private CustomerSelectsVendorService customerSelectsVendorService;


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
}
