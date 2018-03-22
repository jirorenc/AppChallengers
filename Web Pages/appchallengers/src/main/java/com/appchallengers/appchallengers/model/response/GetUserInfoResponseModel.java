package com.appchallengers.appchallengers.model.response;

public class GetUserInfoResponseModel {

    private long id;
    private String fullname;
    private String profilepicture;
    private String country;
    private long useractionid;
    private int status;
    private long challenges;
    private long accepted_challenges;
    private long friends;

    public GetUserInfoResponseModel(long id, String fullname, String profilepicture, String country, long useractionid, int status, long challenges, long accepted_challenges, long friends) {
        this.id = id;
        this.fullname = fullname;
        this.profilepicture = profilepicture;
        this.country = country;
        this.useractionid = useractionid;
        this.status = status;
        this.challenges = challenges;
        this.accepted_challenges = accepted_challenges;
        this.friends = friends;
    }

    public GetUserInfoResponseModel() {
    }
}
