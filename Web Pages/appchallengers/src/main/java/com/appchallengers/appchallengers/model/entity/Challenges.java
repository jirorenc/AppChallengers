package com.appchallengers.appchallengers.model.entity;

import com.appchallengers.appchallengers.model.response.ChallengeResponse;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Challenges.getUserChallenges", query = "SELECT" +
                " CHALLENGE_DETAİL_USER_İD,FULLNAME,PROFİLEPİCTURE,CHALLENGEDETAİL.CHALLENGE_İD AS CHALLENGE_İD," +
                " CHALLENGE_URL,HEADLİNE,CASE WHEN V.VOTE_USER_İD = ? THEN 1 ELSE 0 END AS vote," +
                " COUNT(V2.İD) AS LİKES" +
                " FROM CHALLENGES" +
                "  JOIN CHALLENGEDETAİL ON CHALLENGES.İD = CHALLENGEDETAİL.CHALLENGE_İD" +
                "  LEFT JOIN USERS ON CHALLENGEDETAİL.CHALLENGE_DETAİL_USER_İD = USERS.İD" +
                "  LEFT JOIN VOTES AS V ON CHALLENGEDETAİL.İD = V.CHALLENGE_DETAİL_İD AND V.VOTE_USER_İD = ?" +
                "  LEFT JOIN VOTES AS V2 ON CHALLENGEDETAİL.İD = V2.CHALLENGE_DETAİL_İD" +
                " WHERE CHALLENGE_USER_İD =?" +
                " GROUP BY CHALLENGE_DETAİL_USER_İD, FULLNAME, PROFİLEPİCTURE, CHALLENGEDETAİL.CHALLENGE_İD," +
                "  CHALLENGE_URL, HEADLİNE, V.VOTE_USER_İD, APPCHALLENGERS.CHALLENGEDETAİL.CREATE_DATE" +
                " ORDER BY APPCHALLENGERS.CHALLENGEDETAİL.CREATE_DATE DESC", resultSetMapping = "getUserChallenges"),
        @NamedNativeQuery(name = "Challenges.getAcceptedChallenges", query = "SELECT" +
                " CHALLENGE_DETAİL_USER_İD,FULLNAME,PROFİLEPİCTURE,CHALLENGEDETAİL.CHALLENGE_İD AS CHALLENGE_İD," +
                " CHALLENGE_URL,HEADLİNE,CASE WHEN V.VOTE_USER_İD = ? THEN 1 ELSE 0 END AS vote," +
                " COUNT(V2.İD) AS LİKES" +
                " FROM CHALLENGEDETAİL" +
                "  JOIN CHALLENGES C ON CHALLENGEDETAİL.CHALLENGE_İD = C.İD" +
                "  LEFT JOIN USERS ON CHALLENGEDETAİL.CHALLENGE_DETAİL_USER_İD = USERS.İD" +
                "  LEFT JOIN VOTES AS V ON CHALLENGEDETAİL.İD = V.CHALLENGE_DETAİL_İD AND V.VOTE_USER_İD = ?" +
                "  LEFT JOIN VOTES AS V2 ON CHALLENGEDETAİL.İD = V2.CHALLENGE_DETAİL_İD" +
                " WHERE CHALLENGE_DETAİL_USER_İD =? AND C.CHALLENGE_USER_İD !=?" +
                " GROUP BY CHALLENGE_DETAİL_USER_İD, FULLNAME, PROFİLEPİCTURE, CHALLENGEDETAİL.CHALLENGE_İD," +
                "  CHALLENGE_URL, HEADLİNE, V.VOTE_USER_İD, APPCHALLENGERS.CHALLENGEDETAİL.CREATE_DATE" +
                " ORDER BY APPCHALLENGERS.CHALLENGEDETAİL.CREATE_DATE DESC",resultSetMapping = "getUserChallenges")
})
@SqlResultSetMapping(name = "getUserChallenges",
        classes = @ConstructorResult(
                targetClass = ChallengeResponse.class,
                columns = {
                        @ColumnResult(name = "challenge_detail_user_id", type = long.class),
                        @ColumnResult(name = "fullname"),
                        @ColumnResult(name = "profilepicture"),
                        @ColumnResult(name = "challenge_id", type = long.class),
                        @ColumnResult(name = "challenge_url"),
                        @ColumnResult(name = "headline"),
                        @ColumnResult(name = "vote", type = long.class),
                        @ColumnResult(name = "likes", type = long.class)
                }
        ))
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
