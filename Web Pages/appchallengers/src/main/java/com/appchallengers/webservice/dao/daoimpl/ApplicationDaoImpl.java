package com.appchallengers.webservice.dao.daoimpl;

import com.appchallengers.webservice.dao.dao.ApplicationDao;
import com.appchallengers.webservice.model.entity.Country;
import com.appchallengers.webservice.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

    public List<Country> getCountryList() {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        Query countryTypedQuery = entityManager.createNamedQuery("Country.getCountryList");
        return countryTypedQuery.getResultList();
    }
}
