package com.group10.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group10.Service.Interfaces.IHomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group10.Controller.HomeController;
import com.group10.Model.Category;
import com.group10.Model.Service;

@SpringBootTest
public class HomeControllerTest
{
    @Autowired
    private HomeController homeController;
    @MockBean
    private IHomeService homeService;
    private List<Category> expectedCategories;
    private List<Service> expectedServices;
    

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
        when(homeService.trendingServices()).thenReturn(expectedServices);
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.ok(expectedServices);
        assertEquals(homeController.getTrendingServices().toString(), res.toString());
    }

    @Test
    public void getTrendingServicesTest_EmptyList() throws SQLException
    {
        initializeServiceList();
        expectedServices.clear();
        when(homeService.trendingServices()).thenReturn(expectedServices);
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.ok(expectedServices);
        assertEquals(homeController.getTrendingServices().toString(), res.toString());
    }
    @Test
    public void getTrendingServicesTest_SQLException() throws  SQLException
    {
        initializeServiceList();
        when(homeService.trendingServices()).thenThrow(new SQLException("Some issue while retrieving the data"));
        ResponseEntity<List<Service>> res;
        res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        assertEquals(homeController.getTrendingServices(), res);
    }
}
