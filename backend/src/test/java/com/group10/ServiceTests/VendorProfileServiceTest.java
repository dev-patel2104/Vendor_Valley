package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Exceptions.NoInformationFoundException;
import com.group10.Model.*;
import com.group10.Repository.CategoryRepository;
import com.group10.Repository.ServiceImageRepository;
import com.group10.Repository.ServiceRepository;
import com.group10.Repository.VendorRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Exceptions.UserDoesntExistException;
import com.group10.Repository.CustomerRepositoryImpl;
import com.group10.Service.VendorProfileService;

@SpringBootTest
public class VendorProfileServiceTest
{
    @Autowired
    private VendorProfileService vendorProfileService;
    
    @MockBean
    private CustomerRepositoryImpl CustomerRepositoryImpl;
    @MockBean
    private ServiceImageRepository serviceImageRepository;
    @MockBean
    private VendorRepositoryImpl VendorRepositoryImpl;
    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    private SignUpModel user;
    private int userId;
    private Service service;
    private List<String> imageList;
    private List<String> categoryNames;
    private List<Category> categoryList;


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

    private void initializeService()
    {
        service = new Service();
        imageList = new ArrayList<>();
        categoryNames = new ArrayList<>();

        imageList.add("Str1");
        imageList.add("Str2");

        categoryNames.add("Name1");
        categoryNames.add("Name2");

        service.setServiceName("RandomService");
        service.setServiceDescription("RandomDescription");
        service.setServicePrice("RandomPrice");
        service.setImages(imageList);
        service.setCategoryNames(categoryNames);
    }
    private void initializeCategoryList()
    {
        categoryList = new ArrayList<>();
        categoryList.add(new Category());
    }
//    @Test
//    public void getProfile_Successful() throws SQLException, UserDoesntExistException
//    {
//        userId = 5;
//        initializeUser();
//        when(CustomerRepositoryImpl.getUser(Mockito.any(Integer.class))).thenReturn(user);
//        assertEquals(user, vendorProfileService.getProfile(userId));
//    }
//
//    @Test
//    public void getProfile_NegativeUserID() throws SQLException, UserDoesntExistException
//    {
//        userId = -1;
//        initializeUser();
//        when(CustomerRepositoryImpl.getUser(Mockito.any(Integer.class))).thenReturn(user);
//        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(userId));
//    }
//    @Test
//    public void getProfile_UserDoesntExistException() throws SQLException, UserDoesntExistException
//    {
//        userId = 5;
//        initializeUser();
//        when(CustomerRepositoryImpl.getUser(Mockito.any(Integer.class))).thenReturn(null);
//        assertThrows(UserDoesntExistException.class, () -> vendorProfileService.getProfile(userId));
//    }
//    @Test
//    public void getProfile_SQLException() throws SQLException, UserDoesntExistException
//    {
//        userId = 5;
//        initializeUser();;
//        when(CustomerRepositoryImpl.getUser(Mockito.any(Integer.class))).thenThrow(new SQLException("Problem while fetching from database"));
//        assertThrows(SQLException.class, () -> vendorProfileService.getProfile(userId));
//    }
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
        when(VendorRepositoryImpl.getBookingsInfo(userId)).thenReturn(expectedBookingList);
        assertEquals(expectedBookingList, vendorProfileService.getBookings(userId));
    }
    @Test
    public void getBookings_SQLException() throws SQLException, UserDoesntExistException
    {
        userId = 5;
        initializeUser();
        when(VendorRepositoryImpl.getBookingsInfo(userId)).thenThrow(new SQLException("Database issue"));
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
    public void getCategories_Successful() throws SQLException
    {
        initializeUser();
        List<Category>  expectedCategories = new ArrayList<>();
        when(categoryRepository.getCategories()).thenReturn(expectedCategories);
        assertEquals(expectedCategories, vendorProfileService.getCategories());
    }
    @Test
    public void getCategories_SQLException() throws  SQLException
    {
        initializeUser();
        when(categoryRepository.getCategories()).thenThrow(new SQLException("Database Issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.getCategories());
    }

    @Test
    public void addService_Successful() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();
        when(serviceRepository.insertService(service, categoryList)).thenReturn(service);
        assertEquals(service, vendorProfileService.addService(service, categoryList));
    }
    @Test
    public void addService_MissingName() throws  SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setServiceName(null);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        service.setServiceName("");
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));
    }
    @Test
    public void addService_MissingDescription() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setServiceDescription(null);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        service.setServiceDescription("");
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));
    }
    @Test
    public void addService_MissingPrice() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setServicePrice(null);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        service.setServicePrice("");
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));
    }
    @Test
    public void addService_MissingCategoryName() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setCategoryNames(null);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        service.setCategoryNames(new ArrayList<>());
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

    }
    @Test
    public void addService_MissingCategoryInfo() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        categoryList = null;
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        categoryList = new ArrayList<>();
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

    }
    @Test
    public void addService_MissingImages() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setImages(null);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));

        service.setImages(new ArrayList<>());
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.addService(service,categoryList));
    }
    @Test
    public void addService_SQLException() throws SQLException
    {
        initializeService();
        initializeCategoryList();

        when(serviceRepository.insertService(service,categoryList)).thenThrow(new SQLException("Database Issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.addService(service,categoryList));
    }

    @Test
    public void editCompanyDetails_Successful() throws SQLException, NoInformationFoundException
    {
        initializeUser();

        when(VendorRepositoryImpl.editCompanyDetails(user)).thenReturn(user);
        assertEquals(user, vendorProfileService.editCompanyDetails(user));
    }

    @Test
    public void editCompanyDetails_UnSuccessful() throws  SQLException, NoInformationFoundException
    {
        initializeUser();

        when(VendorRepositoryImpl.editCompanyDetails(user)).thenReturn(null);
        assertEquals(null, vendorProfileService.editCompanyDetails(user));
    }
    @Test
    public void editCompanyDetails_NoInformationFoundException()
    {
        user = null;

        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editCompanyDetails(user));

        initializeUser();
        user.setUserId(-1);

        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editCompanyDetails(user));
    }
    @Test
    public void editCompanyDetails_SQLException() throws SQLException
    {
        initializeUser();

        when(VendorRepositoryImpl.editCompanyDetails(user)).thenThrow(new SQLException("Database Issue"));
        assertThrows(SQLException.class, () -> vendorProfileService.editCompanyDetails(user));
    }
    // @Test
    // public void deleteService_Successful() throws SQLException, NoInformationFoundException
    // {
    //     initializeService();

    //     when(serviceRepository.deleteService(any())).thenReturn(true);
    //     assertEquals(true, vendorProfileService.deleteService(service));
    // }
    // @Test
    // public void deleteService_UnSuccessful() throws SQLException, NoInformationFoundException
    // {
    //     initializeService();

    //     when(serviceRepository.deleteService(any())).thenThrow(new NoInformationFoundException("hi"));
    //     assertThrows(NoInformationFoundException.class, () -> vendorProfileService.deleteService(service));
    // }
    @Test
    public void deleteService_NoInformationException()
    {
        service = null;
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.deleteService(service));

        initializeService();
        service.setServiceId(-1);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.deleteService(service));
    }
    // @Test
    // public void deleteService_SQLException() throws SQLException
    // {
    //     initializeService();
    //     when(serviceRepository.deleteService(any())).thenThrow(new SQLException("Database Issue"));
    //     assertThrows(SQLException.class, () -> vendorProfileService.deleteService(service));
    // }
    @Test
    public void editService_Successful() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();
        when(serviceRepository.editService(any(), any())).thenReturn(service);
        when(serviceImageRepository.editServiceImage(any())).thenReturn(service);
        assertEquals(service, vendorProfileService.editService(service, categoryList));
    }
    @Test
    public void editService_OnlyInfoNoImage() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        service.setImages(null);
        when(serviceRepository.editService(any(), any())).thenReturn(service);
        assertEquals(service, vendorProfileService.editService(service,categoryList));

        service.setImages(new ArrayList<>());
        assertEquals(service, vendorProfileService.editService(service,categoryList));
    }
    @Test
    public void editService_ImageInfoNotUpdatedProperly() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();
        when(serviceImageRepository.editServiceImage(any())).thenReturn(null);
        assertEquals(null, vendorProfileService.editService(service, categoryList));
    }
    @Test
    public void editService_TextualInfoNotUpdatedProperly() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();
        when(serviceImageRepository.editServiceImage(any())).thenReturn(service);
        when(serviceRepository.editService(any(), any())).thenReturn(null);
        assertEquals(null, vendorProfileService.editService(service, categoryList));
    }
    @Test
    public void editService_NoInformationFoundException() throws SQLException, NoInformationFoundException
    {
        service = null;
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editService(service,categoryList));

        initializeService();
        categoryList = null;
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editService(service,categoryList));

        categoryList = new ArrayList<>();
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editService(service,categoryList));

        service.setServiceId(-1);
        assertThrows(NoInformationFoundException.class, () -> vendorProfileService.editService(service,categoryList));

    }
    @Test
    public void editService_SQLException() throws SQLException, NoInformationFoundException
    {
        initializeService();
        initializeCategoryList();

        when(serviceImageRepository.editServiceImage(any())).thenThrow(new SQLException("Database Issue"));
        when(serviceRepository.editService(any(), any())).thenThrow(new SQLException("Database Issue"));

        assertThrows(SQLException.class, () -> vendorProfileService.editService(service, categoryList));
    }
}
