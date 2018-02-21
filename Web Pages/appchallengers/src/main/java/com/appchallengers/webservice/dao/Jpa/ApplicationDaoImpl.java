package com.appchallengers.webservice.dao.Jpa;

import com.appchallengers.webservice.dao.ApplicationDao;
import com.appchallengers.webservice.model.Country;
import com.appchallengers.webservice.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

    public List<Country> getCountryList() {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Country> countryTypedQuery = entityManager.createNamedQuery("Country.getCountryList", Country.class);
        return countryTypedQuery.getResultList();
    }
}
