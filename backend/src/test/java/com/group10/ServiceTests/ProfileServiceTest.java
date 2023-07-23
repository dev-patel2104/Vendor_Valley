package com.group10.ServiceTests;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Repository.UserRepository;
import com.group10.Repository.VendorRepository;
import com.group10.Service.CustomerProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileServiceTest
{
    // here using CustomerProfileService object but in this class only the parents method would be tested nothing else
    @Autowired
    private CustomerProfileService profileService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private VendorRepository vendorRepository;

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
        when(userRepository.getUser(any(Integer.class))).thenReturn(user);
        assertEquals(user, profileService.getProfile(userId));
    }

    @Test
    public void getProfile_NegativeUserID() throws SQLException
    {
        userId = -1;
        initializeUser();
        when(userRepository.getUser(any(Integer.class))).thenReturn(user);
        assertThrows(UserDoesntExistException.class, () -> profileService.getProfile(userId));
    }
    @Test
    public void getProfile_UserDoesntExistException() throws SQLException
    {
        userId = 5;
        initializeUser();
        when(userRepository.getUser(any(Integer.class))).thenReturn(null);
        assertThrows(UserDoesntExistException.class, () -> profileService.getProfile(userId));
    }
    @Test
    public void getProfile_SQLException() throws SQLException
    {
        userId = 5;
        initializeUser();
        when(userRepository.getUser(any(Integer.class))).thenThrow(new SQLException("Problem while fetching from database"));
        assertThrows(SQLException.class, () -> profileService.getProfile(userId));
    }
    @Test
    public void editProfile_Successful() throws SQLException, NoInformationFoundException
    {
        initializeUser();
        when(userRepository.updateUser(any())).thenReturn(true);
        assertEquals(true, profileService.editProfile(user));
    }
    @Test
    public void editProfile_UnSuccessful() throws SQLException, NoInformationFoundException
    {
        initializeUser();
        when(userRepository.updateUser(any())).thenReturn(false);
        assertEquals(false, profileService.editProfile(user));
    }
    @Test
    public void editProfile_NoInformationFoundException()
    {
        user = null;
        assertThrows(NoInformationFoundException.class, () -> profileService.editProfile(user));

        initializeUser();
        user.setUserId(-1);
        assertThrows(NoInformationFoundException.class, () -> profileService.editProfile(user));
    }
    @Test
    public void editProfile_SQLException() throws SQLException
    {
        initializeUser();
        when(userRepository.updateUser(any())).thenThrow(new SQLException("Database Issue"));
        assertThrows(SQLException.class, () -> profileService.editProfile(user));
    }
}
