package com.appchallengers.appchallengers.dao.Jpa;



import com.appchallengers.appchallengers.dao.ApplicationDao;
import com.appchallengers.appchallengers.model.Country;
import com.appchallengers.appchallengers.util.JpaFactory;

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
