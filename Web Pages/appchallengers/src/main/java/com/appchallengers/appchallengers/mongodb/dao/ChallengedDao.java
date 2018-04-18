package com.appchallengers.appchallengers.mongodb.dao;

import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;

import java.util.List;


public interface ChallengedDao {

    void insert(ChallengedModel challengedModel);
    List<ChallengedModel> getUserNatification(long toId);
}
