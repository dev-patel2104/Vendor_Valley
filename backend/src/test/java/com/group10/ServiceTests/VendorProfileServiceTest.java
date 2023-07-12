package com.group10.ServiceTests;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Repository.UserRepository;
import com.group10.Repository.VendorRepository;
import com.group10.Service.CustomerProfileService;
import com.group10.Service.VendorProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VendorProfileServiceTest
{
    @InjectMocks
    private VendorProfileService vendorProfileService;
    @Mock
    private UserRepository userRepository;

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
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        assertEquals(user, vendorProfileService.getProfile(user_id));
    }

    @Test(expected = UserDoesntExistException.class)
    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
    {
        user_id = -1;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(user);
        vendorProfileService.getProfile(user_id);
    }

    @Test(expected = UserDoesntExistException.class)
    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        initializeUser();
        when(userRepository.getUser(Mockito.any(Integer.class))).thenReturn(null);
        vendorProfileService.getProfile(user_id);
    }

    @Test(expected = SQLException.class)
    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
    {
        user_id = 5;
        initializeUser();;
        when(userRepository.getUser(Mockito.any(Integer.class))).thenThrow(new SQLException("Problem while fetching from database"));
        vendorProfileService.getProfile(user_id);
    }
}
