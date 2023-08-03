package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;

public class RatingComparator implements Comparator<Service> {
    @Override
    public int compare(Service s1, Service s2) {
        double rating1 = 0.0;
        double rating2 = 0.0;
        try {
            rating1 = Double.parseDouble(s1.getAverageRating());
        } catch (NumberFormatException | NullPointerException  e) {
            rating1 = 0.0;
        }
        try {
            rating2 = Double.parseDouble(s2.getAverageRating());
        } catch (NumberFormatException | NullPointerException e) {
            rating2 = 0.0;
        }
        return Double.compare(rating1, rating2);
    }
}