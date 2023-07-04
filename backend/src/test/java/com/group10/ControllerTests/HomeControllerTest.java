package com.group10.ControllerTests;

import com.group10.Controller.HomeController;
import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Service.HomeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest
{
    @InjectMocks
    private HomeController homeController;
    @Mock
    private HomeService homeService;
    private List<Category> expectedCategories;
    private List<Service> expectedServices;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    private void initializeCategoryList()
    {
        Category cat = new Category();
        expectedCategories = new ArrayList<>();
        cat.setCategoryId(1);
        expectedCategories.add(cat);
        cat = new Category();
        cat.setCategoryId(2);
        expectedCategories.add(cat);
        cat = new Category();
        cat.setCategoryId(3);
        expectedCategories.add(cat);
    }
    private void initializeServiceList()
    {
        expectedServices = new ArrayList<>();
        Service ser = new Service();
        ser.setServiceId(1);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(2);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(3);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(4);
        expectedServices.add(ser);
        ser = new Service();
        ser.setServiceId(5);
        expectedServices.add(ser);
    }
    @Test
    public void getFeaturedCategoriesTest() throws SQLException
    {
        initializeCategoryList();
        when(homeService.featuredCategories()).thenReturn(expectedCategories);
        ResponseEntity<List<Category>> res;
        res = ResponseEntity.ok(expectedCategories);
        assertEquals(homeController.getFeaturedCategories().toString(), res.toString());
    }
    @Test
    public void getFeaturedCategoriesTest_EmptyList() throws  SQLException
    {
        initializeCategoryList();
        expectedCategories.clear();
        when(homeService.featuredCategories()).thenReturn(expectedCategories);
        ResponseEntity<List<Category>> res;
        res = ResponseEntity.ok(expectedCategories);
        assertEquals(homeController.getFeaturedCategories().toString(), res.toString());
    }
    @Test
    public void getFeaturedCategoriesTest_SQLException() throws SQLException
    {
        initializeCategoryList();
        when(homeService.featuredCategories()).thenThrow(new SQLException("Some issue with the DB connection"));
        ResponseEntity<List<Category>> res;
        res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(homeController.getFeaturedCategories().toString(), res.toString());
    }

    @Test
    public void getTrendingServicesTest() throws SQLException
    {
        initializeServiceList();
        when(homeService.TrendingServices()).thenReturn(expectedServices);
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.ok(expectedServices);
        assertEquals(homeController.getTrendingServices().toString(), res.toString());
    }

    @Test
    public void getTrendingServicesTest_EmptyList() throws SQLException
    {
        initializeServiceList();
        expectedServices.clear();
        when(homeService.TrendingServices()).thenReturn(expectedServices);
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.ok(expectedServices);
        assertEquals(homeController.getTrendingServices().toString(), res.toString());
    }
    @Test
    public void getTrendingServicesTest_SQLException() throws  SQLException
    {
        initializeServiceList();
        when(homeService.TrendingServices()).thenThrow(new SQLException("Some issue while retrieving the data"));
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(homeController.getTrendingServices(), res);
    }
}
