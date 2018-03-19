package com.appchallengers.webservice.dao;

import com.appchallengers.webservice.model.Users;

public interface UserDao {

    void saveUser(Users user);
    Users findByEmail(String email);
    Long checkEmail(String email);
    Long checkIdAndPasswordSalt(Integer id,String passwordSalt);
    void confirmEmail(Integer id,String passwordSalt);
    void changePassword(Integer id,String passwordHash);
    Users findUserById(Integer id);
    Long login(String email,String passwordHash);

}
