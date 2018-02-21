package com.appchallengers.webservice.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "Users.findUserByEmail", query = "SELECT user from Users user where user.email=:email"),
        @NamedQuery(name = "Users.checkEmail", query = "SELECT COUNT(user) from Users user where user.email=:email"),
        @NamedQuery(name = "Users.checkIdAndPasswordSalt", query = "SELECT COUNT(user) from Users user where user.id=:id and user.passwordSalt=:passwordSalt"),
        @NamedQuery(name="Users.login",query = "select count(user)from Users user where user.email=:email and user.passwordHash=:passwordHash")
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
}
