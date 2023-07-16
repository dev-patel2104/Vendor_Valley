package com.group10.Util;

import java.util.Comparator;
import java.util.List;

import com.group10.Model.Service;

public class ComparatorUtil {

    private Comparator<Service> comparator;
    private boolean ascending;

    public ComparatorUtil(Comparator<Service> comparator, boolean ascending) {
        this.comparator = comparator;
        this.ascending = ascending;
    }

    public void sort(List<Service> services) {
        if (ascending) {
            services.sort(comparator);
        } else {
            services.sort(comparator.reversed());
        }
    }
}