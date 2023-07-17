package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Booking;
import com.group10.Model.Service;
import com.group10.Model.User;
import com.group10.Repository.CategoryRepository;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Repository.UserRepository;
import com.group10.Service.VendorProfileService;

@SpringBootTest
public class VendorProfileServiceTest
{
    @Autowired
    private VendorProfileService vendorProfileService;
    
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private VendorRepository vendorRepository;
    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    private SignUpModel user;
    private int userId;


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
        userId = 5;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        assertEquals(user, vendorProfileService.getProfile(userId));
    }

    @Test
    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
    {
        userId = -1;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(userId));
    }
    @Test
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(userId));
    }
    @Test
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();;
        when(userRepository.getUser(Mockito.any(Integer.class))).thenThrow(new SQLException("Problem while fetching from database"));
        assertThrows(SQLException.class, () -> vendorProfileService.getProfile(userId));
    }
    @Test
    public void getServices_Successful() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        List<Service> expectedServiceList = new ArrayList<>();
        when(serviceRepository.getServicesForVendor(userId)).thenReturn(expectedServiceList);
        assertEquals(expectedServiceList, vendorProfileService.getServices(userId));
    }
    @Test
    public void getServices_SQLException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        when(serviceRepository.getServicesForVendor(userId)).thenThrow(new SQLException("Database issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.getServices(userId));
    }
    @Test
    public void getService_UserDoesntException() throws SQLException, UserDoesntExistException
    {
        userId = -2;
        initializeUser();
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getServices(userId));
    }
    @Test
    public void getBookings_Successful() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        List<Booking> expectedBookingList = new ArrayList<>();
        when(vendorRepository.getBookingsInfo(userId)).thenReturn(expectedBookingList);
        assertEquals(expectedBookingList, vendorProfileService.getBookings(userId));
    }
    @Test
    public void getBookings_SQLException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        when(vendorRepository.getBookingsInfo(userId)).thenThrow(new SQLException("Database issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.getBookings(userId));
    }
    @Test
    public void getBookings_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        userId = -2;
        initializeUser();
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getBookings(userId));
    }
    @Test
    public void getCategoryNames_Successful() throws SQLException
    {
        initializeUser();
        List<String>  expectedCategoryNames = new ArrayList<>();
        when(categoryRepository.getCategoryName()).thenReturn(expectedCategoryNames);
        assertEquals(expectedCategoryNames, vendorProfileService.getCategoryNames());
    }
    @Test
    public void getCategoryNames_SQLException() throws  SQLException
    {
        initializeUser();
        when(categoryRepository.getCategoryName()).thenThrow(new SQLException("Database Issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.getCategoryNames());
    }
}
