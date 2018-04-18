package com.appchallengers.appchallengers.dao.dao;

import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;

import java.util.List;

public interface UserNatifications {

    List<ChallengedModel> getUserNatifications(long toId);
}
