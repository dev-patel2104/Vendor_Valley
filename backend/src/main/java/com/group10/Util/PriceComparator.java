package com.group10.Util;

import java.util.Comparator;

import com.group10.Model.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceComparator implements Comparator<Service> {

    @Override
    public int compare(Service s1, Service s2) {
        log.debug("Comparing prices for services: " + s1.getServiceName() + " and " + s2.getServiceName());

        int price1 = 0;
        int price2 = 0;
        try {
            price1 = Integer.parseInt(s1.getServicePrice());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Error parsing price for service " + s1.getServiceName() + ": " + e.getMessage());
        }
        try {
            price2 = Integer.parseInt(s2.getServicePrice());
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Error parsing price for service " + s2.getServiceName() + ": " + e.getMessage());
        }

        log.debug("Comparison result: " + Integer.compare(price1, price2));
        return Integer.compare(price1, price2);
    }
}