package com.appchallengers.webservice.model.response;



public class AddChallengeResponse {

    private Integer challenge_detail_user_id;
    private String fullname;
    private String profilepicture;
    private Integer challenge_detail_id;
    private String headline;
    private String challenge_url;
    private long likes;
    private long dislikes;
    private Integer votes;

    public AddChallengeResponse(Integer challenge_detail_user_id, String fullname, String profilepicture, Integer challenge_detail_id, String headline,String challenge_url, long likes, long dislikes,Integer votes) {
        this.challenge_detail_user_id = challenge_detail_user_id;
        this.fullname = fullname;
        this.profilepicture = profilepicture;
        this.challenge_detail_id = challenge_detail_id;
        this.headline = headline;
        this.challenge_url = challenge_url;
        this.likes = likes;
        this.dislikes = dislikes;
        this.votes=votes;
    }

    public AddChallengeResponse() {
    }
}
