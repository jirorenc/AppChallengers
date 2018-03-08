package com.appchallengers.appchallengers.web.bussiness;



import com.appchallengers.appchallengers.dao.Jpa.UserDaoImpl;
import com.appchallengers.appchallengers.dao.UserDao;
import com.appchallengers.appchallengers.model.Users;
import com.appchallengers.appchallengers.util.Util;

import java.security.NoSuchAlgorithmException;

public class BussinessLogin {
 UserDao userDao=new UserDaoImpl();

    // This metod cheks the account info from db
    public boolean accountControl(String email , String password) throws NoSuchAlgorithmException {

       long kontrol=userDao.login(email, Util.hashMD5(password));
      if(kontrol==1){
          return  true;
      }
        return false;
    }

    // Signup oparation mehod
    public boolean  signupUser(Users users){
      userDao.saveUser(users);

        return true;
    }

    // send mail and checking method
    public boolean  confirmMail(String mail){
        if(mail.equals("onelfurkan@gmail.com")){
            return true;
        }
        return false;
    }

    public  boolean checkConfirm(String email){
        int kontrol= userDao.findByEmail(email).getActive().ordinal();
        if(kontrol==1){
            return true;
        }
        return false;
    }


}
