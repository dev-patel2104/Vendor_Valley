package com.group10.Service.Interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.group10.Model.Service;

public interface ISearchService {
    public List<Service> getSearchResults(String searchParam) throws SQLException;
    public List<Service> sortSearchResults(List<Service> services, String sortParam, String sortOrder);
    public List<Service> filterSearchResults(List<Service> services, Map<String, String> Sortvalues);
}
