package com.appchallengers.webservice.model;

import javax.persistence.*;

@Entity
public class Confirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String confirmKey;
    @OneToOne(fetch = FetchType.LAZY)
    private Users users;

    public Confirm(String confirmKey, Users users) {
        this.confirmKey = confirmKey;
        this.users = users;
    }

    public Confirm() {
    }
}
