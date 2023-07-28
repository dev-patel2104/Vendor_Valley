package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Model.Booking;
import com.group10.Repository.Interfaces.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Repository.CustomerRepositoryImpl;
import com.group10.Service.CustomerProfileService;

@SpringBootTest
public class CustomerProfileServiceTest
{
    @Autowired
    private CustomerProfileService customerProfileService;
    
    @MockBean
    private ICustomerRepository CustomerRepositoryImpl;

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
        initializeUser();
        List<SignUpModel> users = new ArrayList<>();
        users.add(user);
        userId = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(users);
        assertEquals(user, customerProfileService.getProfile(userId));
    }

    @Test
    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        List<SignUpModel> users = new ArrayList<>();
        users.add(user);
        userId = -1;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(users);
        assertThrows(UserDoesntExistException.class, () -> customerProfileService.getProfile(userId));
    }

    @Test
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        userId = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> customerProfileService.getProfile(userId));
    }

    @Test
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        userId = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenThrow(new SQLException("Problem while fetching from database"));
        assertThrows(SQLException.class, () -> customerProfileService.getProfile(userId));
    }

    @Test
    public void getBookings_Successful() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        List<Booking> expectedBookingList = new ArrayList<>();
        when(CustomerRepositoryImpl.getBookings(userId)).thenReturn(expectedBookingList);
        assertEquals(expectedBookingList, customerProfileService.getBookings(userId));
    }
    @Test
    public void getBookings_SQLException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        when(CustomerRepositoryImpl.getBookings(userId)).thenThrow(new SQLException("Database issue"));
        assertThrows(SQLException.class, () -> customerProfileService.getBookings(userId));
    }
    @Test
    public void getBookings_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        userId = -2;
        initializeUser();
        assertThrows(UserDoesntExistException.class, () -> customerProfileService.getBookings(userId));
    }
}
