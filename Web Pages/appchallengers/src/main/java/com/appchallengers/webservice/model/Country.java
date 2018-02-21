package com.appchallengers.webservice.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Country.getCountryList",query = "select country from Country country")
})
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String countryCode;
    private String countryName;
}
