package com.appchallengers.webservice.dao.dao;

import com.appchallengers.webservice.model.entity.Country;

import java.util.List;

public interface ApplicationDao {

    List<Country> getCountryList();
}
