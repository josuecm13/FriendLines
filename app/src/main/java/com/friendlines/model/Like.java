package com.friendlines.model;

public class Like extends FirebaseItem {
    private String user_id;
    //likes can happen both on posts and comments,
    //so a collection id must be specified to differentiate
    //either comments or posts
    private int value;

    public Like(){}

    public Like(String user_id, int value){
        this.user_id = user_id;
        this.value = value;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getValue() {
        return value;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
