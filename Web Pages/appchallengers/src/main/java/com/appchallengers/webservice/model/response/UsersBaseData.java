package com.appchallengers.webservice.model.response;

public class UsersBaseData {

    private long id;
    private String fullName;
    private String profile_picture;

    public UsersBaseData(long id, String fullName, String profile_picture) {
        this.id = id;
        this.fullName = fullName;
        this.profile_picture = profile_picture;
    }

    public UsersBaseData() {
    }
}
