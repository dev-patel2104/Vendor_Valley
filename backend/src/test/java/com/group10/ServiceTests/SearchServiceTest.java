package com.group10.ServiceTests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.group10.Service.Interfaces.ISearchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Model.Service;
import com.group10.Repository.ServiceRepository;

@SpringBootTest
public class SearchServiceTest {
    
    @MockBean 
    private ServiceRepository serviceRepository;

    @MockBean
    private Service service;

    @Autowired
    private ISearchService searchService;


    @Test
    public void testNullReturn_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        Mockito.doReturn(null).when(serviceRepository).getServicesBasedOnSearchParam(searchParam);
        assertNull(searchService.getSearchResults(searchParam));
    }

    @Test
    public void testEmptyList_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        List<Service> expected = new ArrayList<>();
        Mockito.doReturn(expected).when(serviceRepository).getServicesBasedOnSearchParam(searchParam);
        assertEquals(expected, searchService.getSearchResults(searchParam));
    }

    @Test
    public void testSizeEquals_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        List<Service> expected = new ArrayList<>();
        expected.add(service);
        Mockito.doReturn(expected).when(serviceRepository).getServicesBasedOnSearchParam(searchParam);
        assertEquals(expected.size(), searchService.getSearchResults(searchParam).size());
    }
    
    @Test
    public void testSQLException_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(serviceRepository).getServicesBasedOnSearchParam(searchParam);
        assertThrows(SQLException.class, () -> searchService.getSearchResults(searchParam).size());
    }

    // @Test
    // public void testNullReturn_sortSearchResults(){
    //     // Mock repository layer and check if the correct results are returned
    //     List<Service> services = null;
    //     String sortParam = "test";
    //     String sortOrder = "asc";
    //     assertNull(searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testEmptyList_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     String sortParam = "test";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testNullSortParam_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = null;
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testEmptySortParam_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testNullSortOrder_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "test";
    //     String sortOrder = null;
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testEmptySortOrder_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "test";
    //     String sortOrder = "";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortOrderAsc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "test";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortOrderDesc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "test";
    //     String sortOrder = "desc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortOrderInvalid_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     services.add(service);
    //     String sortParam = "test";
    //     String sortOrder = "invalid";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamPriceAsc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setServicePrice("1");
    //     Service service2 = new Service();
    //     service2.setServicePrice("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "price";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamPriceDesc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setServicePrice("1");
    //     Service service2 = new Service();
    //     service2.setServicePrice("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "price";
    //     String sortOrder = "desc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamPriceInvalid_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setServicePrice("1");
    //     Service service2 = new Service();
    //     service2.setServicePrice("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "price";
    //     String sortOrder = "invalid";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamRatingAsc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setAverageRating("1");
    //     Service service2 = new Service();
    //     service2.setAverageRating("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "rating";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamRatingDesc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setAverageRating("1");
    //     Service service2 = new Service();
    //     service2.setAverageRating("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "rating";
    //     String sortOrder = "desc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamRatingInvalid_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setAverageRating("1");
    //     Service service2 = new Service();
    //     service2.setAverageRating("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "rating";
    //     String sortOrder = "invalid";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamBookingAsc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setTotalBookings("1");
    //     Service service2 = new Service();
    //     service2.setTotalBookings("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "booking";
    //     String sortOrder = "asc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamBookingDesc_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setTotalBookings("1");
    //     Service service2 = new Service();
    //     service2.setTotalBookings("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "booking";
    //     String sortOrder = "desc";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    // @Test
    // public void testSortParamBookingInvalid_sortSearchResults(){
    //     List<Service> services = new ArrayList<>();
    //     Service service1 = new Service();
    //     service1.setTotalBookings("1");
    //     Service service2 = new Service();
    //     service2.setTotalBookings("2");
    //     services.add(service1);
    //     services.add(service2);
    //     String sortParam = "booking";
    //     String sortOrder = "invalid";
    //     assertEquals(services, searchService.sortSearchResults(services, sortParam, sortOrder));
    // }

    @Test
    public void testNullReturn_filterSearchResults(){
        List<Service> services = null;
        Map<String, String> filterParams = null;
        assertNull(searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testEmptyList_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testNullFilterParams_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        services.add(service);
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testEmptyFilterParams_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        services.add(service);
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testFilterParamsPrice_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        Service service1 = new Service();
        service1.setServicePrice("1");
        Service service2 = new Service();
        service2.setServicePrice("2");
        services.add(service1);
        services.add(service2);
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testFilterParamsRating_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        Service service1 = new Service();
        service1.setAverageRating("1");
        Service service2 = new Service();
        service2.setAverageRating("2");
        services.add(service1);
        services.add(service2);
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

    @Test
    public void testFilterParamsBooking_filterSearchResults(){
        List<Service> services = new ArrayList<>();
        Service service1 = new Service();
        service1.setTotalBookings("1");
        Service service2 = new Service();
        service2.setTotalBookings("2");
        services.add(service1);
        services.add(service2);
        Map<String, String> filterParams = null;
        assertEquals(services, searchService.filterSearchResults(services, filterParams));
    }

}
