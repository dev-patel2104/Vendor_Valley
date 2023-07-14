package com.group10.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.group10.Model.Service;
import com.group10.Repository.ServiceRepository;
import com.group10.Service.Interfaces.ISearchService;

@org.springframework.stereotype.Service
public class SearchService implements ISearchService{
    @Autowired
    private ServiceRepository searchRepository;

    @Override
    public List<Service> getSearchResults(String searchParam) throws SQLException {
        // Call search repository
        try{
            return searchRepository.getServicesBasedOnSearchParam(searchParam);
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
    }

    public List<Service> sortSearchResults(List<Service> services, String sortParam, String sortOrder) {
        // Sort the services based on the sort params one by one
        if (sortOrder == null || sortOrder.equals("")){
            sortOrder = "asc";
        }
        if (sortOrder.equalsIgnoreCase("asc")){
            services = sortAsc(services, sortParam);
        }
        else if (sortOrder.equalsIgnoreCase("desc")){
            services = sortDesc(services, sortParam);
        }
        return services;
    }

    private List<Service> sortDesc(List<Service> services, String sortParam) {
        // Sort the services based on the sort param
        if(sortParam == null || sortParam.equals(""))
        {
            return services;
        }

        if (sortParam.equalsIgnoreCase("price")){   
            // Sort by price
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getServicePrice()) < Integer.parseInt(services.get(j).getServicePrice()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase("rating")){
            // Sort by rating
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Double.parseDouble(services.get(i).getAverageRating()) < Double.parseDouble(services.get(j).getAverageRating()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase("bookings")){
            // Sort by bookings
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getTotalBookings()) < Integer.parseInt(services.get(j).getTotalBookings()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        return services;
    }

    private List<Service> sortAsc(List<Service> services, String sortParam) {
        // Sort the services based on the sort param
        if(sortParam == null || sortParam.equals(""))
        {
            return services;
        }

        if (sortParam.equalsIgnoreCase("price")){   
            // Sort by price
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getServicePrice()) > Integer.parseInt(services.get(j).getServicePrice()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase("rating")){
            // Sort by rating
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Double.parseDouble(services.get(i).getAverageRating()) > Double.parseDouble(services.get(j).getAverageRating()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        else if (sortParam.equalsIgnoreCase("bookings")){
            // Sort by bookings
            for (int i = 0; i < services.size(); i++)
            {
                for (int j = i + 1; j < services.size(); j++)
                {
                    if (Integer.parseInt(services.get(i).getTotalBookings()) > Integer.parseInt(services.get(j).getTotalBookings()))
                    {
                        Service temp = services.get(i);
                        services.set(i, services.get(j));
                        services.set(j, temp);
                    }
                }
            }
        }
        return services;
    }
    
    public List<Service> filterSearchResults(List<Service> services, Map<String, String> filterValues){
        // Filter the services based on the filter params one by one
        if (filterValues == null || filterValues.size() == 0){
            return services;
        }
        for (Map.Entry<String, String> entry : filterValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            services = filterSearchResults(services, key, value);
        }
        return services;
    }

    private List<Service> filterSearchResults(List<Service> services, String key, String value) {
        // Filter the services based on the filter param
        if(key == null || key.equals("") || value == null || value.equals(""))
        {
            return services;
        }
        // Filter process
        if (key.equalsIgnoreCase("price")){
            // Filter by price
            for (int i = 0; i < services.size(); i++)
            {
                if (Integer.parseInt(services.get(i).getServicePrice()) > Integer.parseInt(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase("rating")){
            // Filter by rating
            for (int i = 0; i < services.size(); i++)
            {
                if (Double.parseDouble(services.get(i).getAverageRating()) < Double.parseDouble(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase("bookings")){
            // Filter by bookings
            for (int i = 0; i < services.size(); i++)
            {
                if (Integer.parseInt(services.get(i).getTotalBookings()) < Integer.parseInt(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        else if (key.equalsIgnoreCase("category")){
            // Filter by category
            for (int i = 0; i < services.size(); i++)
            {
                if (!services.get(i).getCategoryNames().contains(value))
                {
                    services.remove(i);
                    i--;
                }
            }
        }
        return services;
    }
}
