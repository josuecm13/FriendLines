package com.friendlines.model;

public class Education extends FirebaseItem
{
    public String user_id;
    public String institution;
    public String type;

    public Education(){}

    public Education(String user_id, String institution, String type){
        this.user_id  = user_id;
        this.institution = institution;
        this.type = type;
    }
}