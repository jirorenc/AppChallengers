package com.appchallengers.webservice.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Users.findUserByEmail", query = "SELECT user from Users user where user.email=:email"),
        @NamedQuery(name = "Users.checkEmail", query = "SELECT COUNT(user) from Users user where user.email=:email"),
        @NamedQuery(name = "Users.checkIdAndPasswordSalt", query = "SELECT COUNT(user) from Users user where user.id=:id and user.passwordSalt=:passwordSalt"),
        @NamedQuery(name = "Users.login", query = "select count(user)from Users user where user.email=:email and user.passwordHash=:passwordHash")
})
public class Users {

    public static enum Active {NOT_CONFİRMED, ACTIVE, FROZEN}

    @Id
    @SequenceGenerator(name = "LICENSE_SEQ", sequenceName = "LICENSE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LICENSE_SEQ")
    private long id;
    private String fullName;
    private String email;
    private String passwordHash;
    private String passwordSalt;
    private String country;
    private String profilePicture;
    @Enumerated(EnumType.ORDINAL)
    private Active active;
    private Timestamp createDate;
    private Timestamp updateDate;

    @OneToMany(orphanRemoval = true, mappedBy = "firstUser", cascade = {CascadeType.ALL})
    private List<Relationship> relationships_one = new LinkedList<Relationship>();

    @OneToMany(orphanRemoval = true, mappedBy = "secondUser", cascade = {CascadeType.ALL})
    private List<Relationship> relationships_two = new LinkedList<Relationship>();

    @OneToMany(orphanRemoval = true, mappedBy = "challenge_user", cascade = {CascadeType.ALL})
    private List<Challenges> challengesList = new LinkedList<Challenges>();

    @OneToMany(orphanRemoval = true, mappedBy = "challenge_detail_user", cascade = {CascadeType.ALL})
    private List<ChallengeDetail> challengeDetailList = new LinkedList<ChallengeDetail>();

    @OneToMany(orphanRemoval = true, mappedBy = "vote_user", cascade = {CascadeType.ALL})
    private List<Votes> votes = new LinkedList<Votes>();

    public Users() {
    }

    public Users(String fullName, String email, String passwordHash, String passwordSalt, String country, String profilePicture, Active active, Timestamp createDate, Timestamp updateDate) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.country = country;
        this.profilePicture = profilePicture;
        this.active = active;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Users(String fullName, String email, String passwordHash, String passwordSalt, String country, Active active, Timestamp createDate, Timestamp updateDate) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.country = country;
        this.active = active;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Active getActive() {
        return active;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getCountry() {
        return country;
    }
}
