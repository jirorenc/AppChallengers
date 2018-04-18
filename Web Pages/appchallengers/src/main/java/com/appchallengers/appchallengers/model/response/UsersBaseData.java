package com.appchallengers.appchallengers.model.response;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
