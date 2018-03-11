package com.appchallengers.webservice.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Challenges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String headLine;
    private Timestamp createDate;
    private String challenge_video_url;
    private long likes;
    private long dislikes;
    @ManyToOne
    private Users challenge_user;
    @OneToMany(orphanRemoval = true, mappedBy = "challenge_detail", cascade = {CascadeType.ALL})
    private List<ChallengeDetail> challengeDetailList = new LinkedList<ChallengeDetail>();

    public Challenges() {
    }

    public Challenges(String headLine, Timestamp createDate, String challenge_video_url, long likes, long dislikes, Users challenge_user) {
        this.headLine = headLine;
        this.createDate = createDate;
        this.challenge_video_url = challenge_video_url;
        this.likes = likes;
        this.dislikes = dislikes;
        this.challenge_user = challenge_user;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getChallenge_video_url() {
        return challenge_video_url;
    }

    public void setChallenge_video_url(String challenge_video_url) {
        this.challenge_video_url = challenge_video_url;
    }

    public Users getChallenge_user() {
        return challenge_user;
    }

    public void setChallenge_user(Users challenge_user) {
        this.challenge_user = challenge_user;
    }

    public List<ChallengeDetail> getChallengeDetailList() {
        return challengeDetailList;
    }

    public void setChallengeDetailList(List<ChallengeDetail> challengeDetailList) {
        this.challengeDetailList = challengeDetailList;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }
}
