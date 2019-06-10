package com.friendlines.model;

import com.google.firebase.firestore.Exclude;

public abstract class Identifiable {
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
