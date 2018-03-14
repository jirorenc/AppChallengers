package com.appchallengers.webservice.model.response;



public class ChallengeResponse {

    private long challenge_detail_user_id;
    private String fullname;
    private String profilepicture;
    private long challenge_detail_id;
    private String challenge_url;
    private String headline;
    private long vote;
    private long likes;

    public ChallengeResponse(long challenge_detail_user_id, String fullname, String profilepicture, long challenge_detail_id, String challenge_url, String headline, long vote, long likes) {
        this.challenge_detail_user_id = challenge_detail_user_id;
        this.fullname = fullname;
        this.profilepicture = profilepicture;
        this.challenge_detail_id = challenge_detail_id;
        this.challenge_url = challenge_url;
        this.headline = headline;
        this.vote = vote;
        this.likes = likes;
    }

    public ChallengeResponse() {
    }
}
