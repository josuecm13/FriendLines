package com.friendlines.model;

public class Like extends Identifiable {
    public static final int LIKED_VALUE = 1;
    public static final int DISLIKED_VALUE = -1;

    private String user_id;
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
