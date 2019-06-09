package com.friendlines.model;

import com.google.firebase.Timestamp;

public class User extends FirebaseItem {
    public String auth_id;
    public String firstname;
    public String lastname;
    public String image;
    public Timestamp birthday;
    public int phone;
    public String gender;
    public String city;
    public String country;

    public User(){}

    public User(String auth_id, String firstname, String lastname, String image, Timestamp birthday, int phone, String gender, String city, String country){
        this.auth_id = auth_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
        this.country = country;
    }
}
