package com.appchallengers.webservice.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Reaction {

    public static enum Type {LİKE,DISLIKE,NO_ACTİON};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ChallengeDetail challenge_detail_reaction;
    @ManyToOne
    private Users reaction_user;
    @Enumerated(value = EnumType.ORDINAL)
    private Type type;
    private Timestamp create_date;
}
