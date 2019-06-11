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

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Identifiable))
            return false;
        else if(this.getId() == null || ((Identifiable)o).getId() == null)
            return false;
        else
            return ((Identifiable)o).getId().equals(this.getId());
    }
}
