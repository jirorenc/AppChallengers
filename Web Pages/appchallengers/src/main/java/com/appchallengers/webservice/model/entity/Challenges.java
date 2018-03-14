package com.appchallengers.webservice.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Challenges {

    @Id
    @SequenceGenerator(name = "LICENSE_SEQ", sequenceName = "LICENSE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LICENSE_SEQ")
    private long id;
    private String headLine;
    private Timestamp createDate;
    @ManyToOne
    private Users challenge_user;
    @OneToMany(orphanRemoval = true, mappedBy = "challenge", cascade = {CascadeType.ALL})
    private List<ChallengeDetail> challengeDetailList = new LinkedList<ChallengeDetail>();

    public Challenges() {
    }

    public Challenges(String headLine, Timestamp createDate, Users challenge_user) {
        this.headLine = headLine;
        this.createDate = createDate;
        this.challenge_user = challenge_user;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
