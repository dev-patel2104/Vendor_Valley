package com.group10.ServiceTests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.group10.Model.Service;
import com.group10.Repository.SearchRepository;
import com.group10.Service.SearchService;

@SpringBootTest
public class SearchServiceTest {
    
    @MockBean 
    private SearchRepository searchRepository;

    @MockBean
    private Service service;

    @Autowired
    private SearchService searchService;


    @Test
    public void testNullReturn_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        Mockito.doReturn(null).when(searchRepository).getSearchResults(searchParam);
        assertNull(searchService.getSearchResults(searchParam));
    }

    @Test
    public void testEmptyList_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        List<Service> expected = new ArrayList<>();
        Mockito.doReturn(expected).when(searchRepository).getSearchResults(searchParam);
        assertEquals(expected, searchService.getSearchResults(searchParam));
    }

    @Test
    public void testSizeEquals_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        List<Service> expected = new ArrayList<>();
        expected.add(service);
        Mockito.doReturn(expected).when(searchRepository).getSearchResults(searchParam);
        assertEquals(expected.size(), searchService.getSearchResults(searchParam).size());
    }
    
    @Test
    public void testSQLException_getSearchResults() throws SQLException {
        // Mock repository layer and check if the correct results are returned
        String searchParam = "test";
        Mockito.doThrow(new SQLException("Db Connection Lost!")).when(searchRepository).getSearchResults(searchParam);
        assertThrows(SQLException.class, () -> searchService.getSearchResults(searchParam).size());
    }

}
