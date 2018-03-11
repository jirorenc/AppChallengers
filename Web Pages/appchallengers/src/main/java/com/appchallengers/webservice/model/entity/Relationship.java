package com.appchallengers.webservice.model.entity;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Relationship.checkRelationship", query = "SELECT COUNT(relationship) from Relationship relationship where relationship.firstUser.id=:firstuser and relationship.secondUser.id=:seconduser"),
        @NamedQuery(name = "Relationship.getRelationship", query = "SELECT relationship from Relationship relationship where relationship.firstUser.id=:firstuser and relationship.secondUser.id=:seconduser"),
        @NamedQuery(name = "Relationship.getRelationships", query = "SELECT u from Users u,Relationship r where u.id=case when r.firstUser.id=:userid then r.secondUser.id when r.secondUser.id=:userid then r.firstUser.id else -1 end and r.status=:param")
})
public class Relationship {

    public static enum Type {SEND_REQUEST, FRIEND}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    private Users firstUser;
    @ManyToOne()
    private Users secondUser;
    private Integer userActionId;
    @Enumerated(EnumType.ORDINAL)
    private Type status;

    public Relationship() {
    }
    public Relationship(Users firstUser, Users secondUser, Integer userActionId, Type status) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.userActionId = userActionId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(Integer userActionId) {
        this.userActionId = userActionId;
    }

    public Type getStatus() {
        return status;
    }

    public void setStatus(Type status) {
        this.status = status;
    }
}
