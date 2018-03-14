package com.appchallengers.webservice.dao.dao;

import com.appchallengers.webservice.model.entity.Users;

public interface UserDao {

    Users saveUser(Users user);
    Users findByEmail(String email);
    Long checkEmail(String email);
    Long checkIdAndPasswordSalt(long id,String passwordSalt);
    void confirmEmail(long id,String passwordSalt);
    void changePassword(long id,String passwordHash);
    Users findUserById(long id);
    Long login(String email,String passwordHash);

}
