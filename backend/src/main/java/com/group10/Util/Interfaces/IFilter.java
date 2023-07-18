package com.group10.Util.Interfaces;

import java.util.List;

import com.group10.Model.Service;

public interface IFilter {
    List<Service> execute(List<Service> services);
}
