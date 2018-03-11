package com.appchallengers.webservice.model.response;

public class UserPreferenceData {

    private String  token;
    private String fullName;
    private String imageUrl;
    private String email;
    private Integer active;

    public UserPreferenceData(String token, String fullName, String imageUrl, String email, Integer active) {
        this.token = token;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.email = email;
        this.active = active;
    }

    public UserPreferenceData() {
    }
}
