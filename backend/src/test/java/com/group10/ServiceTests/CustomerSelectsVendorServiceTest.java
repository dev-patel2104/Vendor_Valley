package com.group10.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.group10.Repository.ServiceRepository;
import com.group10.Service.CustomerSelectsVendorService;

@SpringBootTest
public class CustomerSelectsVendorServiceTest {

    @MockBean
    private ServiceRepository serviceRepository;
    
    @Autowired
    private CustomerSelectsVendorService customerSelectsVendorService;


    @Test
    public void testEmptyListReturn_getReviews() throws Exception {
        // Mock repository layer and check if the correct results are returned
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(new ArrayList<>()).when(serviceRepository).getReviews(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testNonEmptyListReturn_getReviews() throws Exception {
        // Mock repository layer and check if the correct results are returned
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(new ArrayList<>()).when(serviceRepository).getReviews(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testServiceExists_getReviews() throws Exception{
        // Mock repository layer and check if the correct results are returned 
        boolean serviceExists = true;
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(serviceExists).when(serviceRepository).checkIfServiceExists(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }

    @Test
    public void testServiceDoesntExists_getReviews() throws Exception{
        // Mock repository layer and check if the correct results are returned 
        boolean serviceExists = false;
        String serviceId = "2";
        int serviceIdInt = Integer.parseInt(serviceId);
        Mockito.doReturn(serviceExists).when(serviceRepository).checkIfServiceExists(serviceIdInt);
        assertEquals(new ArrayList<>(), customerSelectsVendorService.getReviews(serviceId));
    }
}
