package com.appchallengers.appchallengers.model.response;

public class GetFriendListResponse {

    private Integer id;
    private String fullName;
    private String imageUrl;
    private String email;
    private String country;
    private Integer active;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
