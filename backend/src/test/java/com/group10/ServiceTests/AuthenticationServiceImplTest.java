package com.group10.ServiceTests;

import com.group10.Constants.Constants;
import com.group10.Exceptions.InvalidPasswordException;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Model.Vendor;
import com.group10.Repository.UserRepository;
import com.group10.Repository.VendorRepository;
import com.group10.Service.Interfaces.IAuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceImplTest
{
    @Autowired
    private IAuthenticationService authenticationService;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VendorRepository vendorRepository;

    private SignUpModel signUpModel;
    @Autowired
    private User user;

    private void intializeUser()
    {
        signUpModel = SignUpModel.builder().
                userId(543).
                firstName("Mau").
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
    public void SignInTest() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        when(userRepository.addUser(Mockito.any(User.class))).thenReturn(1);
        when(vendorRepository.saveVendor(Mockito.any(User.class),Mockito.any(Vendor.class))).thenReturn(true);
        boolean signUpUser = authenticationService.SignIn(signUpModel);
        assertEquals(true, signUpUser);

    }

    @Test
    public void SignInTest_FirstNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setFirstName("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setFirstName(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }
    @Test
    public void SignInTest_LastNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();
        signUpModel.setLastName("");

        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setLastName(null);

        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_MobileAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setMobile("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setMobile(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_StreetAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setStreet("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setStreet(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_CityAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setCity("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setCity(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_ProvinceAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setProvince("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setProvince(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_CountryAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setCountry("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setCountry(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_EmailAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setEmail("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setEmail(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setEmail("131euail.com");
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_PasswordAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setPassword("");
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setPassword(null);
        assertEquals(false, authenticationService.SignIn(signUpModel));

        signUpModel.setPassword("IDKTHE");
        assertEquals(false, authenticationService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_VendorTest() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setIsVendor(5);
        assertFalse(authenticationService.SignIn(signUpModel));

        signUpModel.setIsVendor(-1);
        assertFalse(authenticationService.SignIn(signUpModel));

        signUpModel.setIsVendor(0);
        when(userRepository.addUser(any(User.class))).thenReturn(1);
        assertTrue(authenticationService.SignIn(signUpModel));

        signUpModel.setIsVendor(1);
        when(vendorRepository.saveVendor(any(User.class), any(Vendor.class))).thenReturn(true);
        assertTrue(authenticationService.SignIn(signUpModel));
    }

    @Test
    public void SignInTest_UserAlreadyPresentException() throws SQLException, UserAlreadyPresentException {
        intializeUser();
        when(userRepository.addUser(Mockito.any(User.class))).thenReturn(Constants.USERALREADYEXISTS);
        assertThrows(UserAlreadyPresentException.class, () -> authenticationService.SignIn(signUpModel));
    }

    @Test
    public void SignInTest_SQLException() throws SQLException, UserAlreadyPresentException {
        intializeUser();
        when(userRepository.addUser(Mockito.any(User.class))).thenThrow(new SQLException());
        assertThrows(SQLException.class, () -> authenticationService.SignIn(signUpModel));
    }

    @Test
    public void userFound_login() throws SQLException, UserDoesntExistException, InvalidPasswordException {
        String email = "test@mail.com";
        String password = "password";
        user.setPassword(password);
        Mockito.doReturn(user).when(userRepository).findByEmail(email);
        assertEquals(user, authenticationService.login(email,password));
    }

    @Test
    public void userFoundPasswordMismatch_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        user.setPassword(password+"wrong");
        Mockito.doReturn(user).when(userRepository).findByEmail(email);
        assertThrows(InvalidPasswordException.class, () -> authenticationService.login(email,password));
    }

    @Test
    public void userNotFound_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        Mockito.doReturn(null).when(userRepository).findByEmail(email);
        assertThrows(UserDoesntExistException.class, () -> authenticationService.login(email,password));
    }

    @Test
    public void dbConnectionError_login() throws SQLException, UserDoesntExistException, InvalidPasswordException{
        String email = "test@mail.com";
        String password = "password";
        Mockito.doThrow(new SQLException("Database Connection Lost!")).when(userRepository).findByEmail(email);
        assertThrows(SQLException.class, () -> authenticationService.login(email,password));
    }
}
