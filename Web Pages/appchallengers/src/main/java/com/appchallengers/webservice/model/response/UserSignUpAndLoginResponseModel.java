package com.appchallengers.webservice.model.response;

public class UserSignUpAndLoginResponseModel {

    private Integer id;
    private String  token;
    private String email;
    private Integer active;

    public UserSignUpAndLoginResponseModel(Integer id, String token, String email, Integer active) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.active = active;
    }
}
