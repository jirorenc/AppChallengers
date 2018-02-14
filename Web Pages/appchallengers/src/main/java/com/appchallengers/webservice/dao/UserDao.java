package com.appchallengers.webservice.dao;

import com.appchallengers.webservice.model.Users;

import java.util.List;

public interface UserDao {

    void saveUser(Users user);
    Users findByEmail(String email);
    List<Users> checkEmail(String email);
}
