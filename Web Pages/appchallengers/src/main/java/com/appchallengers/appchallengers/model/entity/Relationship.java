package com.appchallengers.appchallengers.model.entity;


import com.appchallengers.appchallengers.model.response.UsersBaseData;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Relationship.checkRelationship", query = "SELECT COUNT(relationship) from Relationship relationship where relationship.firstUser.id=:firstuser and relationship.secondUser.id=:seconduser"),
        @NamedQuery(name = "Relationship.getRelationship", query = "SELECT relationship from Relationship relationship where relationship.firstUser.id=:firstuser and relationship.secondUser.id=:seconduser"),
})
@NamedNativeQuery(name = "Relationship.getRelationships", query = "SELECT" +
        " U.İD,U.FULLNAME,U.PROFİLEPİCTURE as PROFİLE_PİCTURE FROM USERS AS U, RELATİONSHİP AS R" +
        " WHERE U.İD = CASE WHEN R.FİRSTUSER_İD = ? THEN SECONDUSER_İD" +
        " WHEN SECONDUSER_İD = ? THEN FİRSTUSER_İD END AND R.STATUS = 1"
        , resultSetMapping = "Relationship.UsersBaseData")
@SqlResultSetMapping(name = "Relationship.UsersBaseData",
        classes = @ConstructorResult(
                targetClass = UsersBaseData.class,
                columns = {
                        @ColumnResult(name = "id", type = long.class),
                        @ColumnResult(name = "fullName"),
                        @ColumnResult(name = "profile_picture")
                }
        ))
public class Relationship {

    public static enum Type {SEND_REQUEST, FRIEND}

    @Id
    @SequenceGenerator(name = "LICENSE_SEQ", sequenceName = "LICENSE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LICENSE_SEQ")
    private long id;
    @ManyToOne()
    private Users firstUser;
    @ManyToOne()
    private Users secondUser;
    private long userActionId;
    @Enumerated(EnumType.ORDINAL)
    private Type status;

    public Relationship() {
    }

    public Relationship(Users firstUser, Users secondUser, long userActionId, Type status) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.userActionId = userActionId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(Users firstUser) {
        this.firstUser = firstUser;
    }

    public Users getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(Users secondUser) {
        this.secondUser = secondUser;
    }

    public long getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(long userActionId) {
        this.userActionId = userActionId;
    }

    public Type getStatus() {
        return status;
    }

    public void setStatus(Type status) {
        this.status = status;
    }
}
