package com.appchallengers.appchallengers.webservice.response;

/**
 * Created by jir on 16.2.2018.
 */

public class UserPreferencesData {
    private Integer statusCode;
    private Integer id;
    private String token;
    private String email;
    private Integer active;

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
