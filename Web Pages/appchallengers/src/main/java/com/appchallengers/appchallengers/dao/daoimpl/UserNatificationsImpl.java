package com.appchallengers.appchallengers.dao.daoimpl;

import com.appchallengers.appchallengers.dao.dao.UserNatifications;
import com.appchallengers.appchallengers.mongodb.dao.ChallengedDao;
import com.appchallengers.appchallengers.mongodb.daoimpl.ChallengedDaoImpl;
import com.appchallengers.appchallengers.mongodb.model.ChallengedModel;

import java.util.List;

public class UserNatificationsImpl implements UserNatifications {

    ChallengedDao challengedDao = new ChallengedDaoImpl();

    public List<ChallengedModel> getUserNatifications(long toId) {
        return challengedDao.getUserNatification(toId);
    }
}
