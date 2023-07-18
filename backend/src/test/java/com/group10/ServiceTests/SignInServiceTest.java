package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Constants.Constants;
import com.group10.Exceptions.UserAlreadyPresentException;
import com.group10.Model.SignUpModel;
import com.group10.Model.User;
import com.group10.Model.Vendor;
import com.group10.Repository.UserRepository;
import com.group10.Repository.VendorRepository;
import com.group10.Service.SignInService;

@SpringBootTest
public class SignInServiceTest
{
    @Autowired
    private SignInService signInService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VendorRepository vendorRepository;

    private SignUpModel signUpModel;


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
        boolean signUpUser = signInService.SignIn(signUpModel);
        assertEquals(true, signUpUser);

    }

    @Test
    public void SignInTest_FirstNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setFirstName("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setFirstName(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }
    @Test
    public void SignInTest_LastNameAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();
        signUpModel.setLastName("");

        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setLastName(null);

        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_MobileAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setMobile("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setMobile(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_StreetAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setStreet("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setStreet(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_CityAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setCity("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setCity(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_ProvinceAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setProvince("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setProvince(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_CountryAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setCountry("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setCountry(null);
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_EmailAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setEmail("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setEmail(null);
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setEmail("131euail.com");
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_PasswordAbsent() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setPassword("");
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setPassword(null);
        assertEquals(false, signInService.SignIn(signUpModel));

        signUpModel.setPassword("IDKTHE");
        assertEquals(false, signInService.SignIn(signUpModel));

    }

    @Test
    public void SignInTest_VendorTest() throws SQLException, UserAlreadyPresentException
    {
        intializeUser();

        signUpModel.setIsVendor(5);
        assertFalse(signInService.SignIn(signUpModel));

        signUpModel.setIsVendor(-1);
        assertFalse(signInService.SignIn(signUpModel));

        signUpModel.setIsVendor(0);
        when(userRepository.addUser(any(User.class))).thenReturn(1);
        assertTrue(signInService.SignIn(signUpModel));

        signUpModel.setIsVendor(1);
        when(vendorRepository.saveVendor(any(User.class), any(Vendor.class))).thenReturn(true);
        assertTrue(signInService.SignIn(signUpModel));
    }

    @Test
    public void SignInTest_UserAlreadyPresentException() throws SQLException, UserAlreadyPresentException {
        intializeUser();
        when(userRepository.addUser(Mockito.any(User.class))).thenReturn(Constants.USERALREADYEXISTS);
        assertThrows(UserAlreadyPresentException.class, () -> signInService.SignIn(signUpModel));
    }

    @Test
    public void SignInTest_SQLException() throws SQLException, UserAlreadyPresentException {
        intializeUser();
        when(userRepository.addUser(Mockito.any(User.class))).thenThrow(new SQLException());
        assertThrows(SQLException.class, () -> signInService.SignIn(signUpModel));
    }



}

