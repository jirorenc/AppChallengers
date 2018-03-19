package com.appchallengers.appchallengers.web.bussiness;

public class BusinessMainpage {

    public long save_like(int userid , int postid, int like_number){
        // ilk defa begeniyosa  veritabanına likenumber 1 arttırlıp kaydedilir ve metot 1 donderir
        // Eger daha önce begenmisse geri alınır begenisi ve likenumber bir azaltılır metot 0 donderir
        System.out.println("--->>>LİKEEE metodu calıstı");
        return 1;
    }
    public long save_dislike(int userid , int postid, int like_number){
        // ilk defa dislike yapıyaorsa  veritabanına likenumber 1 arttırlıp kaydedilir ve metot 1 donderir
        // Eger daha önce dislike yapmıssa geri alınır begenisi ve likenumber bir azaltılır metot 0 donderir

        System.out.println("---->>>DİSLİKE metodu calıstı");
        return 1;
    }
}
