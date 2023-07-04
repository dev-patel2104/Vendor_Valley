package com.group10.Service;

import com.group10.Model.Category;
import com.group10.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class HomeService
{

    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> featuredCategories() throws SQLException
    {
        return categoryRepository.getFeaturedCategories();
    }

    /* Currently retrieving the services which has the highest number of booking from the users side i.e., to say that we will only
     * consider the bookings made by the user. As long as the booking is made from the user we are not worried whether the booking
     * is accepted by the vendor or not because even if the vendor rejects the booking the service was still opted by the user as
     * their 1st preference.*/
    public List<com.group10.Model.Service> TrendingServices() throws SQLException
    {
        return categoryRepository.getTrendingServices();
    }
}
