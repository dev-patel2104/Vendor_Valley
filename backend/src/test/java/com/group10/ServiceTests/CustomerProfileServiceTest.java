package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private CustomerRepositoryImpl CustomerRepositoryImpl;

    private SignUpModel user;
    private int user_id;


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
        user_id = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(users);
        assertEquals(user, customerProfileService.getProfile(user_id));
    }

    @Test
    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        List<SignUpModel> users = new ArrayList<>();
        users.add(user);
        user_id = -1;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(users);
        assertThrows(UserDoesntExistException.class, () -> customerProfileService.getProfile(user_id));
    }

    @Test
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        user_id = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> customerProfileService.getProfile(user_id));    
    }

    @Test
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        initializeUser();
        user_id = 5;
        when(CustomerRepositoryImpl.getUsers(any())).thenThrow(new SQLException("Problem while fetching from database"));
        assertThrows(SQLException.class, () -> customerProfileService.getProfile(user_id));
    }
}
