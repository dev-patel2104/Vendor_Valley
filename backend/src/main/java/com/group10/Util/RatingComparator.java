package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;
import lombok.extern.slf4j.Slf4j;


/**
 * A comparator implementation to compare services based on their average ratings.
 */
@Slf4j
public class RatingComparator implements Comparator<Service> {

    /**
     * Compares two services based on their average ratings.
     *
     * @param s1 The first service to compare.
     * @param s2 The second service to compare.
     * @return Negative if s1's rating is less than s2's, positive if s1's rating is greater than s2's, zero if equal.
     */
    @Override
    public int compare(Service s1, Service s2) {
        log.debug("Comparing ratings for services: " + s1.getServiceName() + " and " + s2.getServiceName());

        double rating1 = 0.0;
        double rating2 = 0.0;
        try {
            rating1 = Double.parseDouble(s1.getAverageRating());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Error parsing rating for service " + s1.getServiceName() + ": " + e.getMessage());
        }
        try {
            rating2 = Double.parseDouble(s2.getAverageRating());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Error parsing rating for service " + s2.getServiceName() + ": " + e.getMessage());
        }

        log.debug("Rating comparison result: " + Double.compare(rating1, rating2));
        return Double.compare(rating1, rating2);
    }
}