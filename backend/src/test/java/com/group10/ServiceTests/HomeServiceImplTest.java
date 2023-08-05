package com.group10.ServiceTests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Repository.Interfaces.ICategoryRepository;
import com.group10.Repository.Interfaces.IVendorRepository;
import com.group10.Service.Interfaces.IHomeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Model.VendorDashboard;
import com.group10.Util.JWTTokenHandler;

@SpringBootTest
public class HomeServiceImplTest
{
    @Autowired
    private IHomeService homeServiceImpl;
    @MockBean
    private ICategoryRepository categoryRepository;
    @MockBean
    private IVendorRepository VendorRepositoryImpl;
    @MockBean
    private JWTTokenHandler jwtTokenHandler;
    private List<Category> expectedCategories;
    private List<Service> expectedServices;


    private void initializeCategoryList()
    {
        Category cat = new Category();
        expectedCategories = new ArrayList<>();
        cat.setCategoryId(1);
        expectedCategories.add(cat);
        cat = new Category();
        cat.setCategoryId(2);
        expectedCategories.add(cat);
        cat = new Category();
        cat.setCategoryId(3);
        expectedCategories.add(cat);
    }
    private void initializeServiceList()
    {
        expectedServices = new ArrayList<>();
        Service ser = new Service();
        ser.setServiceId(1);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(2);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(3);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(4);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(5);
        expectedServices.add(ser);
    }
    @Test
    public void featuredCategoriesTest() throws SQLException
    {
        initializeCategoryList();
        when(categoryRepository.getFeaturedCategories()).thenReturn(expectedCategories);

        assertEquals(homeServiceImpl.featuredCategories().size(), expectedCategories.size());
    }

    @Test
    public void featuredCategoriesTest_EmptyList() throws  SQLException
    {
        initializeCategoryList();
        expectedCategories.clear();
        when(categoryRepository.getFeaturedCategories()).thenReturn(expectedCategories);
        assertEquals(homeServiceImpl.featuredCategories().size(), expectedCategories.size());
    }
    @Test
    public void featuredCategoriesTest_SQLException() throws SQLException
    {
        initializeCategoryList();
        when(categoryRepository.getFeaturedCategories()).thenThrow(new SQLException("Some issue while retrieving data"));
        assertThrows(SQLException.class, () -> homeServiceImpl.featuredCategories());
    }
    @Test
    public void trendingServicesTest() throws SQLException
    {
        initializeServiceList();
        when(categoryRepository.getTrendingServices()).thenReturn(expectedServices);
        assertEquals(homeServiceImpl.trendingServices().size(), expectedServices.size());
    }
    @Test
    public void trendingServicesTest_EmptyList() throws  SQLException
    {
        initializeServiceList();
        expectedServices.clear();
        when(categoryRepository.getTrendingServices()).thenReturn(expectedServices);
        assertEquals(homeServiceImpl.trendingServices().size(), expectedServices.size());
    }
    @Test
    public void trendingServicesTest_SQLException() throws SQLException
    {
        initializeServiceList();
        when(categoryRepository.getTrendingServices()).thenThrow(new SQLException("Some issue while retrieving data"));
        assertThrows(SQLException.class, () -> homeServiceImpl.trendingServices());
    }

    @Test
    public void testSuccessPath_getVendorDashboardInfo() throws SQLException, JWTVerificationException{
        String token = "jwtToken";
        DecodedJWT decodedJwtToken = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockClaim.asInt()).thenReturn(1);
        Mockito.when(decodedJwtToken.getClaim("userId")).thenReturn(mockClaim);
        Mockito.doReturn(decodedJwtToken).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(new VendorDashboard()).when(VendorRepositoryImpl).getStatistics(Mockito.anyInt());
        VendorDashboard vendorDashboard = homeServiceImpl.getVendorDashboardInfo(token);
        assertEquals(vendorDashboard.getClass(), VendorDashboard.class);
    }

    @Test
    public void testFailurePath_getVendorDashboardInfo() throws SQLException, JWTVerificationException{
        String token = "jwtToken";
        DecodedJWT decodedJwtToken = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockClaim.asInt()).thenReturn(1);
        Mockito.when(decodedJwtToken.getClaim("userId")).thenReturn(mockClaim);
        Mockito.doReturn(decodedJwtToken).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(null).when(VendorRepositoryImpl).getStatistics(Mockito.anyInt());
        VendorDashboard vendorDashboard = homeServiceImpl.getVendorDashboardInfo(token);
        assertNull(vendorDashboard);
    }

    @Test
    public void testSQLException_getVendorDashboardInfo() throws SQLException, JWTVerificationException{
        String token = "jwtToken";
        DecodedJWT decodedJwtToken = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockClaim.asInt()).thenReturn(1);
        Mockito.when(decodedJwtToken.getClaim("userId")).thenReturn(mockClaim);
        Mockito.doReturn(decodedJwtToken).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(VendorRepositoryImpl).getStatistics(Mockito.anyInt());
        assertThrows(SQLException.class, () -> homeServiceImpl.getVendorDashboardInfo(token));
    }

    @Test
    public void testJWTVerificationException_getVendorDashboardInfo() throws SQLException, JWTVerificationException{
        String token = "jwtToken";
        DecodedJWT decodedJwtToken = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockClaim.asInt()).thenReturn(1);
        Mockito.when(decodedJwtToken.getClaim("userId")).thenReturn(mockClaim);
        Mockito.doReturn(decodedJwtToken).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doThrow(new JWTVerificationException("JWT Verification Failed!")).when(VendorRepositoryImpl).getStatistics(Mockito.anyInt());
        assertThrows(JWTVerificationException.class, () -> homeServiceImpl.getVendorDashboardInfo(token));
    }

    @Test
    public void testNullPointerException_getVendorDashboardInfo() throws SQLException, JWTVerificationException{
        String token = "jwtToken";
        DecodedJWT decodedJwtToken = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockClaim.asInt()).thenReturn(1);
        Mockito.when(decodedJwtToken.getClaim("userId")).thenReturn(mockClaim);
        // Mockito.doReturn(decodedJwtToken).when(jwtTokenHandler).decodeJWTToken(token);
        Mockito.doReturn(new VendorDashboard()).when(VendorRepositoryImpl).getStatistics(Mockito.anyInt());
        assertThrows(NullPointerException.class, () -> homeServiceImpl.getVendorDashboardInfo(token));
    }

    @Test
    public void testSuccessPath_getCustomerInfo() throws SQLException{
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        userIds.add(3);
        List<User> expectedUsers = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        expectedUsers.add(user);
        user = new User();
        user.setUserId(2);
        expectedUsers.add(user);
        user = new User();
        user.setUserId(3);
        expectedUsers.add(user);
        Mockito.doReturn(expectedUsers).when(VendorRepositoryImpl).getUsers(userIds);
        List<SignUpModel> users = homeServiceImpl.getCustomerInfo(userIds);
        assertEquals(users.size(), expectedUsers.size());
    }

    @Test
    public void testSQLException_getCustomerInfo() throws SQLException{
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        userIds.add(3);
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(VendorRepositoryImpl).getUsers(userIds);
        assertThrows(SQLException.class, () -> homeServiceImpl.getCustomerInfo(userIds));
    }

    @Test
    public void testFailurePath_getCustomerInfo() throws SQLException{
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        userIds.add(3);
        List<User> expectedUsers = new ArrayList<>();
        Mockito.doReturn(expectedUsers).when(VendorRepositoryImpl).getUsers(userIds);
        List<SignUpModel> users = homeServiceImpl.getCustomerInfo(userIds);
        assertEquals(users.size(), expectedUsers.size());
    }

}
