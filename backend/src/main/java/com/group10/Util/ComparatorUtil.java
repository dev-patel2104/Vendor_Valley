package com.group10.Util;

import java.util.Comparator;
import java.util.List;

import com.group10.Model.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComparatorUtil {

    private Comparator<Service> comparator;
    private boolean ascending;

    /**
     * Constructs a ComparatorUtil instance with the provided comparator and sorting order.
     *
     * @param comparator The comparator used for sorting.
     * @param ascending  True if sorting is in ascending order, false for descending.
     */
    public ComparatorUtil(Comparator<Service> comparator, boolean ascending) {
        this.comparator = comparator;
        this.ascending = ascending;
    }

    /**
     * Sorts the list of services using the provided comparator and sorting order.
     *
     * @param services The list of services to be sorted.
     */
    public void sort(List<Service> services) {
        log.debug("Sorting services " + (ascending ? "in ascending" : "in descending") + " order.");

        if (ascending) {
            services.sort(comparator);
        } else {
            services.sort(comparator.reversed());
        }

        log.debug("Services sorted.");
    }
}