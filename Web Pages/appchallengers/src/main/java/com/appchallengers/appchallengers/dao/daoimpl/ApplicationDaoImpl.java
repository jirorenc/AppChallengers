package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.ApplicationDao;
import com.appchallengers.appchallengers.model.entity.Country;
import com.appchallengers.appchallengers.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

    public List<Country> getCountryList() {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query countryTypedQuery = entityManager.createNamedQuery("Country.getCountryList");
        return countryTypedQuery.getResultList();
    }
}
