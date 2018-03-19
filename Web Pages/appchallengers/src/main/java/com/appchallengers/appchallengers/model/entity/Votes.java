package com.appchallengers.appchallengers.model.entity;

import com.appchallengers.appchallengers.model.response.UsersBaseData;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "Vote.getVote", query = "SELECT * FROM VOTES WHERE VOTES.CHALLENGE_DETAİL_İD=? AND VOTES.VOTE_USER_İD=?", resultClass = Votes.class),
        @NamedNativeQuery(name = "Vote.getVoteList", query = "SELECT" +
                " U.İD,U.FULLNAME,U.PROFİLEPİCTURE as PROFİLE_PİCTURE FROM VOTES" +
                " LEFT JOIN USERS U ON VOTES.VOTE_USER_İD = U.İD" +
                " WHERE CHALLENGE_DETAİL_İD = ?", resultSetMapping = "Vote.UsersBaseData")
})
@SqlResultSetMapping(name = "Vote.UsersBaseData",
        classes = @ConstructorResult(
                targetClass = UsersBaseData.class,
                columns = {
                        @ColumnResult(name = "id", type = long.class),
                        @ColumnResult(name = "fullName"),
                        @ColumnResult(name = "profile_picture")
                }
        ))
public class Votes {

    @Id
    @SequenceGenerator(name = "LICENSE_SEQ", sequenceName = "LICENSE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LICENSE_SEQ")
    private long id;
    @ManyToOne
    private ChallengeDetail challenge_detail;
    @ManyToOne
    private Users vote_user;
    private Timestamp create_date;

    public Votes(ChallengeDetail challenge_detail, Users vote_user, Timestamp create_date) {
        this.challenge_detail = challenge_detail;
        this.vote_user = vote_user;
        this.create_date = create_date;
    }

    public Votes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChallengeDetail getChallenge_detail() {
        return challenge_detail;
    }

    public void setChallenge_detail(ChallengeDetail challenge_detail) {
        this.challenge_detail = challenge_detail;
    }

    public Users getVote_user() {
        return vote_user;
    }

    public void setVote_user(Users vote_user) {
        this.vote_user = vote_user;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }
}
