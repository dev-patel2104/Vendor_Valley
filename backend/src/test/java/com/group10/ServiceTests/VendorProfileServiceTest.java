package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
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
        user_id = 5;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        assertEquals(user, vendorProfileService.getProfile(user_id));
    }

    @Test
    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
    {
        user_id = -1;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(user_id));
    }

    @Test
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(user_id));
    }

    @Test
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        initializeUser();;
        when(userRepository.getUser(Mockito.any(Integer.class))).thenThrow(new SQLException("Problem while fetching from database"));
        assertThrows(SQLException.class, () -> vendorProfileService.getProfile(user_id));
    }
}
