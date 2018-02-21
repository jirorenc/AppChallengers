package com.appchallengers.webservice.model.response;

public class UserSignUpAndLoginResponseModel {

    private Integer statusCode;
    private Integer id;
    private String  token;
    private String email;
    private Integer active;

    public UserSignUpAndLoginResponseModel(Integer statusCode,Integer id, String token, String email, Integer active) {
        this.statusCode=statusCode;
        this.id = id;
        this.token = token;
        this.email = email;
        this.active = active;
    }

    public UserSignUpAndLoginResponseModel(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
