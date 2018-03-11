package com.appchallengers.webservice.model.entity;

import javax.persistence.*;

@NamedNativeQueries({
        @NamedNativeQuery(name = "Country.getCountryList", query = "select * from Country ",resultClass = Country.class)
})
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String countryCode;
    private String countryName;
}
