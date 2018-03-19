package com.appchallengers.appchallengers.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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

    public Integer getId() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public List<Relationship> getRelationships_one() {
        return relationships_one;
    }

    public void setRelationships_one(List<Relationship> relationships_one) {
        this.relationships_one = relationships_one;
    }

    public List<Relationship> getRelationships_two() {
        return relationships_two;
    }

    public void setRelationships_two(List<Relationship> relationships_two) {
        this.relationships_two = relationships_two;
    }
}
