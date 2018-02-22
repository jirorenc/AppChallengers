package com.appchallengers.webservice.model.response;

public class UserSignUpAndLoginResponseModel {

    private Integer statusCode;
    private String  token;
    private String fullName;
    private String imageUrl;
    private String email;
    private Integer active;

    public UserSignUpAndLoginResponseModel(Integer statusCode, String token, String fullName, String imageUrl, String email, Integer active) {
        this.statusCode = statusCode;
        this.token = token;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.email = email;
        this.active = active;
    }

    public UserSignUpAndLoginResponseModel(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
