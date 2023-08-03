package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;

public class PriceComparator implements Comparator<Service> {
    @Override
    public int compare(Service s1, Service s2) {
        int price1 = 0;
        int price2 = 0;
        try {
            price1 = Integer.parseInt(s1.getServicePrice());
        } catch (NumberFormatException | NullPointerException  e) {
            price1 = 0;
        }
        try {
            price2 = Integer.parseInt(s2.getServicePrice());
        } catch (NumberFormatException | NullPointerException  e) {
            price2 = 0;
        }
        return Integer.compare(price1, price2);
    }
}