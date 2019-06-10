package com.friendlines.model;

import com.google.firebase.Timestamp;

public class User extends FirebaseItem {
    private String auth_id;
    private String firstname;
    private String lastname;
    private String email;
    private String image;
    private Timestamp birthday;
    private int phone;
    private String gender;
    private String city;
    private String country;

    public User(){}

    public User(String auth_id, String firstname, String lastname, String email, String image, Timestamp birthday, int phone, String gender, String city, String country){
        this.auth_id = auth_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.image = image;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
        this.city = city;
        this.country = country;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public int getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
