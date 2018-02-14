package com.appchallengers.webservice.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "Users.findUserByEmail", query = "select user from Users user where user.email=:email")
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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL, optional = false)
    private Confirm confirm;

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
}
