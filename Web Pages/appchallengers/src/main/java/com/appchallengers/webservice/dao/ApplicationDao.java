package com.appchallengers.webservice.dao;

import com.appchallengers.webservice.model.Country;

import java.util.List;

public interface ApplicationDao {

    List<Country> getCountryList();
}
