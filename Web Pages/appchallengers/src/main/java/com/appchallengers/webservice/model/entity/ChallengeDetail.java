package com.appchallengers.webservice.model.entity;

import com.appchallengers.webservice.model.response.AddChallengeResponse;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "ChallengeDetail.getUserChallengeDetail", query = "SELECT" +
                "  CHALLENGE_DETAİL_USER_İD,FULLNAME,PROFİLEPİCTURE," +
                "CHALLENGE_DETAİL_İD,HEADLİNE,CHALLENGEDETAİL.CHALLENGE_URL," +
                "  CHALLENGEDETAİL.LİKES,CHALLENGEDETAİL.DİSLİKES,CASE WHEN R.TYPE = 1" +
                "  THEN 1 WHEN R.TYPE = 0 THEN 0 ELSE 2 END as votes FROM CHALLENGEDETAİL" +
                "  LEFT JOIN CHALLENGES ON CHALLENGEDETAİL.CHALLENGE_DETAİL_İD = CHALLENGES.İD" +
                "  LEFT JOIN USERS ON CHALLENGEDETAİL.CHALLENGE_DETAİL_USER_İD = USERS.İD" +
                "  LEFT JOIN REACTİON R ON CHALLENGEDETAİL.İD = R.CHALLENGE_DETAİL_REACTİON_İD AND " +
                "CHALLENGE_DETAİL_USER_İD = REACTİON_USER_İD WHERE CHALLENGE_DETAİL_USER_İD IN" +
                "(SELECT USERS.İD FROM USERS, RELATİONSHİP WHERE USERS.İD = CASE" +
                "                        WHEN FİRSTUSER_İD =?" +
                "                          THEN SECONDUSER_İD" +
                "                        WHEN SECONDUSER_İD = ?" +
                "                          THEN" +
                "                            FİRSTUSER_İD" +
                "                        ELSE -1 END AND STATUS = ?)" +
                "ORDER BY APPCHALLENGERS.CHALLENGEDETAİL.CREATE_DATE DESC", resultSetMapping = "userChallengeFeedList")})

@SqlResultSetMapping(name = "userChallengeFeedList",
        classes = @ConstructorResult(
                targetClass = AddChallengeResponse.class,
                columns = {
                        @ColumnResult(name = "challenge_detail_user_id"),
                        @ColumnResult(name = "fullname"),
                        @ColumnResult(name = "profilepicture"),
                        @ColumnResult(name = "challenge_detail_id"),
                        @ColumnResult(name = "headline"),
                        @ColumnResult(name = "challenge_url"),
                        @ColumnResult(name = "likes"),
                        @ColumnResult(name = "dislikes"),
                        @ColumnResult(name = "votes"),
                }
        ))
public class ChallengeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Challenges challenge_detail;
    @ManyToOne
    private Users challenge_detail_user;
    private String challenge_url;
    private Timestamp create_date;
    private long likes;
    private long dislikes;
    @OneToMany(orphanRemoval = true, mappedBy = "challenge_detail_reaction", cascade = {CascadeType.ALL})
    List<Reaction> reactionList = new LinkedList<Reaction>();

    public ChallengeDetail(Challenges challenge_detail, Users challenge_detail_user, String challenge_url, Timestamp create_date, long likes, long dislikes) {
        this.challenge_detail = challenge_detail;
        this.challenge_detail_user = challenge_detail_user;
        this.challenge_url = challenge_url;
        this.create_date = create_date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public ChallengeDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Challenges getChallenge_detail() {
        return challenge_detail;
    }

    public void setChallenge_detail(Challenges challenge_detail) {
        this.challenge_detail = challenge_detail;
    }

    public Users getChallenge_detail_user() {
        return challenge_detail_user;
    }

    public void setChallenge_detail_user(Users challenge_detail_user) {
        this.challenge_detail_user = challenge_detail_user;
    }

    public String getChallenge_url() {
        return challenge_url;
    }

    public void setChallenge_url(String challenge_url) {
        this.challenge_url = challenge_url;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public List<Reaction> getReactionList() {
        return reactionList;
    }

    public void setReactionList(List<Reaction> reactionList) {
        this.reactionList = reactionList;
    }
}
