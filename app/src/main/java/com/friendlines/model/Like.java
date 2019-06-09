package com.friendlines.model;

public class Like extends FirebaseItem {
    public String user_id;
    //likes can happen both on posts and comments,
    //so a collection id must be specified to differentiate
    //either comments or posts
    public String collection_id;
    public String post_id;
    public int value;

    public Like(){}

    public Like(String user_id, String collection_id, String post_id, int value){
        this.user_id = user_id;
        this.collection_id = collection_id;
        this.post_id = post_id;
        this.value = value;
    }
}
