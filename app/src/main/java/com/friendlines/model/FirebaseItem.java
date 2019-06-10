package com.friendlines.model;

import com.google.firebase.firestore.Exclude;

public class FirebaseItem {
    @Exclude
    private String id;

    @Exclude
    public String getId(){
        return id;
    }

    @Exclude
    public void setId(String id){
        this.id = id;
    }
}
