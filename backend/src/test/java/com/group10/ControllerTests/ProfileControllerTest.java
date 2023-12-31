package com.group10.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.Booking;
import com.group10.Model.Category;
import com.group10.Repository.Interfaces.IServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Controller.ProfileController;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.Service;
import com.group10.Model.SignUpModel;
import com.group10.Service.CustomerProfileService;
import com.group10.Service.VendorProfileService;
import com.group10.Util.JWTTokenHandler;

@SpringBootTest
public class ProfileControllerTest
{
    @Autowired
    private ProfileController profileController;
    @MockBean
    private CustomerProfileService customerProfileService;
    @MockBean
    private VendorProfileService vendorProfileService;
    @MockBean
    private IServiceRepository serviceRepository;
    @MockBean
    private JWTTokenHandler jwtTokenHandler;

    private SignUpModel user;
    private int user_id;
    private Service service;
    private List<String> imageList;
    private List<String> categoryNames;
    private List<Category> categoryList;


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

    private void initializeService()
    {
        service = new Service();
        imageList = new ArrayList<>();
        categoryNames = new ArrayList<>();

        imageList.add("Str1");
        imageList.add("Str2");

        categoryNames.add("Name1");
        categoryNames.add("Name2");

        service.setServiceName("RandomService");
        service.setServiceDescription("RandomDescription");
        service.setServicePrice("RandomPrice");
        service.setImages(imageList);
        service.setCategoryNames(categoryNames);
    }
    private void initializeCategoryList()
    {
        categoryList = new ArrayList<>();
        categoryList.add(new Category());
    }

    @Test
    public void getProfile_Successful() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "validToken";
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
        String encodedToken = "validToken";
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
        String encodedToken = "validToken";
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
    public void getServices_Successful() throws SQLException, UserDoesntExistException {

        user_id = 5;
        String encodedToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        List<Service> expectedServiceList = new ArrayList<>();
        when(vendorProfileService.getServices(user_id)).thenReturn(expectedServiceList);

        ResponseEntity<List<Service>> response = ResponseEntity.ok(expectedServiceList);

        assertEquals(response,profileController.getServices(encodedToken));

    }
    @Test
    public void getServices_SQLException() throws SQLException, UserDoesntExistException {

        user_id = 5;
        String encodedToken = "validToken";
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(Mockito.mock(DecodedJWT.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId").asInt()).thenReturn(user_id);
        when(vendorProfileService.getServices(user_id)).thenThrow(new SQLException("Problem while fetching data from database"));

        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        assertEquals(res,profileController.getServices(encodedToken));
    }

    @Test
    public void getService_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        user_id = -1;
        String encodedToken = "validToken";
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(Mockito.mock(DecodedJWT.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId").asInt()).thenReturn(user_id);
        when(vendorProfileService.getServices(user_id)).thenThrow(new UserDoesntExistException("No Such user is present"));

        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        assertEquals(res,profileController.getServices(encodedToken));
    }
    @Test
    public void getServices_GeneralException() throws SQLException, UserDoesntExistException {
        user_id = 5;
        String encodedToken = "validToken";
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(Mockito.mock(DecodedJWT.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(jwtTokenHandler.decodeJWTToken(encodedToken).getClaim("userId").asInt()).thenReturn(user_id);
        when(vendorProfileService.getServices(user_id)).thenThrow(new RuntimeException("Some unexpected exception occurred"));

        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        assertEquals(res,profileController.getServices(encodedToken));
    }
    @Test
    public void getBookings_Successful() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("isVendor")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);
        List<Booking> expectedBookingList = new ArrayList<>();
        when(vendorProfileService.getBookings(user_id)).thenReturn(expectedBookingList);

        ResponseEntity<List<Booking>> response = ResponseEntity.ok(expectedBookingList);

        assertEquals(response,profileController.getBookings(encodedToken));

        user_id = 2;
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(0);

        when(customerProfileService.getBookings(user_id)).thenReturn(expectedBookingList);

        assertEquals(response,profileController.getBookings(encodedToken));
    }
    @Test
    public void getBookings_SQLException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("isVendor")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new SQLException("Database issue!"));
        ResponseEntity<List<Booking>> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(response,profileController.getBookings(encodedToken));

        user_id = 2;
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new SQLException("Database issue!"));

        assertEquals(response,profileController.getBookings(encodedToken));
    }
    @Test
    public void getBookings_UserDoesntExistException() throws SQLException,UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("isVendor")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new UserDoesntExistException("No such user is present"));

        ResponseEntity<List<Booking>> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        assertEquals(response,profileController.getBookings(encodedToken));

        user_id = 2;
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new UserDoesntExistException("No such user is present"));

        assertEquals(response,profileController.getBookings(encodedToken));
    }
    @Test
    public void getBookings_GeneralException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        String encodedToken = "validToken";
        DecodedJWT token = Mockito.mock(DecodedJWT.class);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(jwtTokenHandler.decodeJWTToken(encodedToken)).thenReturn(token);
        when(token.getClaim("userId")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("isVendor")).thenReturn(Mockito.mock(Claim.class));
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new RuntimeException("Some unexpected exception occurred"));

        ResponseEntity<List<Booking>> response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);

        assertEquals(response,profileController.getBookings(encodedToken));

        user_id = 2;
        when(token.getClaim("userId").asInt()).thenReturn(user_id);
        when(token.getClaim("isVendor").asInt()).thenReturn(1);

        when(vendorProfileService.getBookings(user_id)).thenThrow(new RuntimeException("Some unexpected exception occurred"));

        assertEquals(response,profileController.getBookings(encodedToken));
    }
    @Test
    public void getCategories_Successful() throws SQLException
    {
        List<Category> expectedCategories = new ArrayList<>();
        when(vendorProfileService.getCategories()).thenReturn(expectedCategories);

        ResponseEntity<List<Category>> response = ResponseEntity.ok(expectedCategories);

        assertEquals(response,profileController.getCategories());
    }
    @Test
    public void getCategories_SQLException() throws SQLException
    {
        when(vendorProfileService.getCategories()).thenThrow(new SQLException("Database issue"));
        ResponseEntity<List<String>> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(response, profileController.getCategories());
    }
    @Test
    public void addService_Successful() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        when(vendorProfileService.addService(any(), any())).thenReturn(service);
        ResponseEntity<Service> res = ResponseEntity.ok(service);
        assertEquals(res, profileController.addService(service));
    }
    @Test
    public void addService_UnSuccessful() throws SQLException, NoInformationFoundException
    {
        Service service = new Service();
        List<Category> categoryList = new ArrayList<>();
        when(vendorProfileService.addService(service, categoryList)).thenReturn(null);
        ResponseEntity<Service> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);;
        assertEquals(res, profileController.addService(service));
    }
    @Test
    public void addService_ServiceNotMapped()
    {
        Service service = null;

        ResponseEntity<Service> res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.addService(service));
    }
    @Test
    public void addService_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        doThrow(SQLException.class).when(vendorProfileService).addService(any(), any());

        // Call the method under test
        ResponseEntity<Service> response = profileController.addService(service);

        // Verify the expected response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
    @Test
    public void editCompanyDetails_Successful() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(vendorProfileService.editCompanyDetails(any())).thenReturn(user);
        ResponseEntity<SignUpModel> res = ResponseEntity.ok(user);

        assertEquals(res, profileController.editCompanyDetails(user));
    }
    @Test
    public void editCompanyDetails_UnSuccessful() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(vendorProfileService.editCompanyDetails(user)).thenReturn(null);
        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        assertEquals(res, profileController.editCompanyDetails(user));
    }
    @Test
    public void editCompanyDetails_NoInformationFoundException() throws SQLException, NoInformationFoundException
    {
        user = null;
        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.editCompanyDetails(user));

        initializeUser();
        when(vendorProfileService.editCompanyDetails(user)).thenThrow(new NoInformationFoundException(null));
        res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.editCompanyDetails(user));
    }
    @Test
    public void editCompanyDetails_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(vendorProfileService.editCompanyDetails(user)).thenThrow(new SQLException("Database Issue"));
        ResponseEntity<SignUpModel> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(res, profileController.editCompanyDetails(user));
    }
    @Test
    public void editProfile_Successful() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(customerProfileService.editProfile(user)).thenReturn(true);
        ResponseEntity<String> res = ResponseEntity.ok("Successfully edited the user's profile information");

        assertEquals(res, profileController.editProfile(user));
    }
    @Test
    public void editProfile_UnSuccessful() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(customerProfileService.editProfile(user)).thenReturn(false);
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        assertEquals(res, profileController.editProfile(user));
    }
    @Test
    public void editProfile_NoInformationFoundException() throws SQLException, NoInformationFoundException
    {
        user = null;
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.editProfile(user));

        initializeUser();
        when(customerProfileService.editProfile(user)).thenThrow(new NoInformationFoundException("The userId for the user to be updated is not available"));
        res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.editProfile(user));
    }
    @Test
    public void editProfile_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeUser();
        when(customerProfileService.editProfile(user)).thenThrow(new SQLException("Database Issue"));
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(res, profileController.editProfile(user));
    }
    @Test
    public void deleteService_Successful() throws SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.deleteService(any())).thenReturn(true);

        ResponseEntity<String> res = ResponseEntity.ok("Service has been successfully deleted");
        assertEquals(res, profileController.deleteService(service));
    }
    @Test
    public void deleteService_UnSuccessful() throws  SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.deleteService(any())).thenReturn(false);

        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        assertEquals(res, profileController.deleteService(service));
    }
    @Test
    public void deleteService_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.deleteService(any())).thenThrow(new SQLException("Database Issue"));

        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(res, profileController.deleteService(service));
    }
    @Test
    public void deleteService_NoInformationFoundException() throws SQLException, NoInformationFoundException
    {
        service = null;
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.deleteService(service));

        initializeService();
        when(vendorProfileService.deleteService(any())).thenThrow(new NoInformationFoundException("Requested input is missing"));
        assertEquals(res, profileController.deleteService(service));
    }
    @Test
    public void editService_Successful() throws SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.editService(any(),any())).thenReturn(service);
        ResponseEntity<Service> res = ResponseEntity.ok(service);
        assertEquals(res, profileController.editService(service));
    }
    @Test
    public void editService_UnSuccessful() throws SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.editService(any(),any())).thenReturn(null);
        ResponseEntity<Service> res = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        assertEquals(res, profileController.editService(service));
    }
    @Test
    public void editService_NoInformationFoundException() throws SQLException, NoInformationFoundException
    {
        service = null;
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        assertEquals(res, profileController.editService(service));

        initializeService();
        when(vendorProfileService.editService(any(),any())).thenThrow(new NoInformationFoundException("Requested input not available"));
        assertEquals(res, profileController.editService(service));
    }
    @Test
    public void editService_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeService();

        when(vendorProfileService.editService(any(),any())).thenThrow(new SQLException("Database Issue"));
        ResponseEntity<String> res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(res, profileController.editService(service));
    }

}
