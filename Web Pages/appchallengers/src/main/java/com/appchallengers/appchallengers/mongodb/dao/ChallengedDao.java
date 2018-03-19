package com.appchallengers.appchallengers.mongodb.dao;

import com.appchallengers.appchallengers.model.response.UsersBaseData;

import java.util.List;

public interface ChallengedDao {

    void insert(String challenge_detail_id,List<UsersBaseData> usersBaseData);
}
