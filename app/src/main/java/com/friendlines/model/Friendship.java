package com.friendlines.model;

import com.google.firebase.Timestamp;

public class Friendship extends FirebaseItem
{
    public String sender_id;
    public String sender_name;
    public String receiver_id;
    public Timestamp created;

    public Friendship(){}

    public Friendship(String sender_id, String sender_name, String receiver_id, Timestamp created) {
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.receiver_id = receiver_id;
        this.created = created;
    }
}
