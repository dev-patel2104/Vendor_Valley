package com.group10.ControllerTests;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Controller.ProfileController;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Service;
import com.group10.Model.SignUpModel;
import com.group10.Service.CustomerProfileService;
import com.group10.Service.ProfileService;
import com.group10.Service.VendorProfileService;
import com.group10.Util.JWTTokenHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileControllerTest
{
    @InjectMocks
    private ProfileController profileController;
    @Mock
    private CustomerProfileService customerProfileService;
    @Mock
    private VendorProfileService vendorProfileService;
    @Mock
    private JWTTokenHandler jwtTokenHandler;

    private SignUpModel user;
    private int user_id;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeUser() {
        user = SignUpModel.builder().
                userId(543).
                firstName("Dev").
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
    public void getProfile_Successful() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "encoded";
        initializeUser();
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(decodedJWT);
        when(decodedJWT.getClaim(Mockito.anyString())).thenReturn(claim);
        when(claim.asInt()).thenReturn(user_id);
        when(customerProfileService.getProfile(user_id)).thenReturn(user);
        ResponseEntity<SignUpModel> res = ResponseEntity.ok(user);
        assertEquals(res, profileController.getProfile(encodedToken));
    }
    @Test
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "encoded";
        initializeUser();
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(decodedJWT);
        when(decodedJWT.getClaim(Mockito.anyString())).thenReturn(claim);
        when(claim.asInt()).thenReturn(user_id);
        when(customerProfileService.getProfile(user_id)).thenThrow(new SQLException("Problem while fetching data from database"));
        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(res,profileController.getProfile(encodedToken));
    }

    @Test
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "encoded";
        initializeUser();
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        Claim claim = Mockito.mock(Claim.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(decodedJWT);
        when(decodedJWT.getClaim(Mockito.anyString())).thenReturn(claim);
        when(claim.asInt()).thenReturn(user_id);
        when(customerProfileService.getProfile(user_id)).thenThrow(new UserDoesntExistException("No such user is present"));
        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        assertEquals(res,profileController.getProfile(encodedToken));
    }

    @Test
    public void getServices_Successful() throws SQLException {
        // Arrange
        String jwtToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(jwtToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(5);
        List<Service> expectedServiceList = Arrays.asList(new Service(), new Service());
        when(vendorProfileService.getServices(5)).thenReturn(expectedServiceList);

        // Act
        ResponseEntity<List<Service>> response = profileController.getServices(jwtToken);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedServiceList, response.getBody());
    }
    @Test
    public void getServices_SQLException() throws SQLException {
        // Arrange
        String jwtToken = "validToken";
        when(jwtTokenHandler.decodeJWTToken(jwtToken)).thenReturn(Mockito.mock(DecodedJWT.class));
        when(jwtTokenHandler.decodeJWTToken(jwtToken).getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(jwtTokenHandler.decodeJWTToken(jwtToken).getClaim("userId").asInt()).thenReturn(5);
        when(vendorProfileService.getServices(5)).thenThrow(new SQLException("Problem while fetching data from database"));

        // Act
        ResponseEntity<List<Service>> response = profileController.getServices(jwtToken);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void getServices_GeneralException() throws SQLException {
        // Arrange
        String jwtToken = "validToken";
        when(jwtTokenHandler.decodeJWTToken(jwtToken)).thenReturn(Mockito.mock(DecodedJWT.class));
        when(jwtTokenHandler.decodeJWTToken(jwtToken).getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(jwtTokenHandler.decodeJWTToken(jwtToken).getClaim("userId").asInt()).thenReturn(5);
        when(vendorProfileService.getServices(5)).thenThrow(new RuntimeException("Some unexpected exception occurred"));

        // Act
        ResponseEntity<List<Service>> response = profileController.getServices(jwtToken);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
