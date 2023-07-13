package com.group10.ServiceTests;

import com.group10.Model.Category;
import com.group10.Model.Service;
import com.group10.Repository.CategoryRepository;
import com.group10.Service.HomeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeServiceTest
{
    @InjectMocks
    private HomeService homeService;
    @Mock
    private CategoryRepository categoryRepository;
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
    public void featuredCategoriesTest() throws SQLException
    {
        initializeCategoryList();
        when(categoryRepository.getFeaturedCategories()).thenReturn(expectedCategories);

        assertEquals(homeService.featuredCategories().size(), expectedCategories.size());
    }

    @Test
    public void featuredCategoriesTest_EmptyList() throws  SQLException
    {
        initializeCategoryList();
        expectedCategories.clear();
        when(categoryRepository.getFeaturedCategories()).thenReturn(expectedCategories);
        assertEquals(homeService.featuredCategories().size(), expectedCategories.size());
    }
    @Test(expected = SQLException.class)
    public void featuredCategoriesTest_SQLException() throws SQLException
    {
        initializeCategoryList();
        when(categoryRepository.getFeaturedCategories()).thenThrow(new SQLException("Some issue while retrieving data"));
        homeService.featuredCategories();
    }
    @Test
    public void trendingServicesTest() throws SQLException
    {
        initializeServiceList();
        when(categoryRepository.getTrendingServices()).thenReturn(expectedServices);
        assertEquals(homeService.TrendingServices().size(), expectedServices.size());
    }
    @Test
    public void trendingServicesTest_EmptyList() throws  SQLException
    {
        initializeServiceList();
        expectedServices.clear();
        when(categoryRepository.getTrendingServices()).thenReturn(expectedServices);
        assertEquals(homeService.TrendingServices().size(), expectedServices.size());
    }
    @Test(expected = SQLException.class)
    public void trendingServicesTest_SQLException() throws SQLException
    {
        initializeServiceList();
        when(categoryRepository.getTrendingServices()).thenThrow(new SQLException("Some issue while retrieving data"));
        homeService.TrendingServices();
    }
}
