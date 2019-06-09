package com.friendlines.model.post;

import com.friendlines.model.FirebaseItem;
import com.google.firebase.Timestamp;

public class Post extends FirebaseItem
{
    public String user_id;
    public String user_name;
    public String user_image;
    public String type;
    public String text;
    public Timestamp created;

    public Post(){}

    public Post(String user_id, String user_name, String user_image, String type, String text, Timestamp created) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_image = user_image;
        this.type = type;
        this.text = text;
        this.created = created;
    }
}
