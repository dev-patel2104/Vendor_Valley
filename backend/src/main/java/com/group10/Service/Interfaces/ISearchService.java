package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;

import com.group10.Model.Service;

public interface ISearchService {
    public List<Service> getSearchResults(String searchParam) throws SQLException;
}
