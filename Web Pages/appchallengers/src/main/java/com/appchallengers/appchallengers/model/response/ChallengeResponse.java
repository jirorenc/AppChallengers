package com.appchallengers.appchallengers.model.response;



public class ChallengeResponse {

    private long challenge_detail_user_id;
    private String fullname;
    private String profilepicture;
    private long challenge_id;
    private long challenge_detail_id;
    private String challenge_url;
    private String headline;
    private long vote;
    private long likes;

    public ChallengeResponse(long challenge_detail_user_id, String fullname, String profilepicture, long challenge_id, long challenge_detail_id, String challenge_url, String headline, long vote, long likes) {
        this.challenge_detail_user_id = challenge_detail_user_id;
        this.fullname = fullname;
        this.profilepicture = profilepicture;
        this.challenge_id = challenge_id;
        this.challenge_detail_id = challenge_detail_id;
        this.challenge_url = challenge_url;
        this.headline = headline;
        this.vote = vote;
        this.likes = likes;
    }

    public ChallengeResponse() {
    }

    public long getChallenge_detail_user_id() {
        return challenge_detail_user_id;
    }

    public void setChallenge_detail_user_id(long challenge_detail_user_id) {
        this.challenge_detail_user_id = challenge_detail_user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public long getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(long challenge_id) {
        this.challenge_id = challenge_id;
    }

    public long getChallenge_detail_id() {
        return challenge_detail_id;
    }

    public void setChallenge_detail_id(long challenge_detail_id) {
        this.challenge_detail_id = challenge_detail_id;
    }

    public String getChallenge_url() {
        return challenge_url;
    }

    public void setChallenge_url(String challenge_url) {
        this.challenge_url = challenge_url;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
}
