package com.group10.ServiceTests;

import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.User;
import com.group10.Repository.UserRepository;
import com.group10.Service.SignInService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInServiceTest
{
    @InjectMocks
    private SignInService signInService;

    @Mock
    private UserRepository userRepository;

    private User user = new User();
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private void intializeUser()
    {
        user.setFirstName("Manu");
        user.setLastName("Patel");
        user.setMobile("9099929025");
        user.setVendor(0);
        user.setStreet("111 Highpark");
        user.setCity("Toronto");
        user.setProvince("ON");
        user.setCountry("Canada");
        user.setEmail("131eu@gmail.com");
        user.setPassword("IDKTHEPASSWORD");
    }
    @Test
    public void SignInTest() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        when(userRepository.addUser(user)).thenReturn(true);
        assertEquals(true, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_FirstNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();
        user.setFirstName("");


        assertEquals(false, signInService.SignIn(user));

        user.setFirstName(null);

        assertEquals(false, signInService.SignIn(user));

    }
    @Test
    public void SignInTest_LastNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();
        user.setLastName("");

        assertEquals(false, signInService.SignIn(user));

        user.setLastName(null);

        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_MobileAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setMobile("");
        assertEquals(false, signInService.SignIn(user));

        user.setMobile(null);
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_StreetAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setStreet("");
        assertEquals(false, signInService.SignIn(user));

        user.setStreet(null);
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_CityAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setCity("");
        assertEquals(false, signInService.SignIn(user));

        user.setCity(null);
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_ProvinceAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setProvince("");
        assertEquals(false, signInService.SignIn(user));

        user.setProvince(null);
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_CountryAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setCountry("");
        assertEquals(false, signInService.SignIn(user));

        user.setCountry(null);
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_EmailAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setEmail("");
        assertEquals(false, signInService.SignIn(user));

        user.setEmail(null);
        assertEquals(false, signInService.SignIn(user));

        user.setEmail("131euail.com");
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_PasswordAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setPassword("");
        assertEquals(false, signInService.SignIn(user));

        user.setPassword(null);
        assertEquals(false, signInService.SignIn(user));

        user.setPassword("IDKTHE");
        assertEquals(false, signInService.SignIn(user));

    }

    @Test
    public void SignInTest_VendorTest() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        user.setVendor(5);
        assertEquals(false, signInService.SignIn(user));

        user.setVendor(-1);
        assertEquals(false, signInService.SignIn(user));

    }
    @Test(expected = UserAlreadyPresentException.class)
    public void SignInTest_UserAlreadyPresentException() throws SQLException, UserAlreadyPresentException {
        User user = new User();
        user.setFirstName("Manu");
        user.setLastName("Patel");
        user.setMobile("9099929025");
        user.setVendor(0);
        user.setStreet("111 Highpark");
        user.setCity("Toronto");
        user.setProvince("ON");
        user.setCountry("Canada");
        user.setEmail("131eu@gmail.com");
        user.setPassword("IDKTHEPASSWORD");


        when(userRepository.addUser(user)).thenReturn(false);
        signInService.SignIn(user);
    }

    @Test(expected = SQLException.class)
    public void SignInTest_SQLException() throws SQLException, UserAlreadyPresentException {
        User user = new User();
        user.setFirstName("Manu");
        user.setLastName("Patel");
        user.setMobile("9099929025");
        user.setVendor(0);
        user.setStreet("111 Highpark");
        user.setCity("Toronto");
        user.setProvince("ON");
        user.setCountry("Canada");
        user.setEmail("131eu@gmail.com");
        user.setPassword("IDKTHEPASSWORD");


        when(userRepository.addUser(user)).thenThrow(new SQLException());
        signInService.SignIn(user);
    }



}

