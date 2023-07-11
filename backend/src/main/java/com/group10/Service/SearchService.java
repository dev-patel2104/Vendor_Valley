package com.group10.Service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.group10.Model.Service;
import com.group10.Repository.SearchRepository;
import com.group10.Service.Interfaces.ISearchService;

@org.springframework.stereotype.Service
public class SearchService implements ISearchService{
    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<Service> getSearchResults(String searchParam) throws SQLException {
        // Call search repository
        try{
            return searchRepository.getSearchResults(searchParam);
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }
    
}
